package com.cbadmin.service.impl.cache;

import com.cbadmin.common.constant.DateFormats;
import com.cbadmin.common.constant.Keys;
import com.cbadmin.common.constant.enums.UserType;
import com.cbadmin.common.systemconfig.SystemConfigs;
import com.cbadmin.common.util.TokenUtil;
import com.cbadmin.dao.entity.User;
import com.cbadmin.dao.repo.UserRepo;
import com.cbadmin.model.param.user.AddUser;
import com.cbadmin.model.param.user.Login;
import com.cbadmin.model.param.user.UpdateUser;
import com.cbadmin.model.vo.UserV;
import com.cbmai.common.exception.BizException;
import com.cbmai.common.result.RInfos;
import com.cbmai.common.util.Base64Util;
import com.cbmai.common.util.BizAssert;
import com.cbmai.core.converter.ObjectConvertTool;
import com.cbmai.core.gauth.GoogleAuthenticator;
import com.cbmai.core.id.IdGenerator;
import com.cbmai.core.password.PasswordHelper;
import com.cbmai.core.util.StringUtils;
import com.cbmai.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 用户缓存服务
 */
@Service
@Slf4j
public class UserCacheService {

    @Autowired
    private GoogleAuthenticator googleAuthenticator;

    @Autowired
    private PasswordHelper passwordHelper;

    @Autowired
    private SystemConfigs systemConfigs;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RedisService redisService;

    @Resource(name = "commonSelfIdGenerator")
    private IdGenerator idGenerator;

    /**
     * 通过id获取用户
     *
     * @param userId
     * @return
     */
    @Caching(
            cacheable = @Cacheable(value = Keys.CACHE_USER, key = "#userId", unless = "#result == null"),
            put = @CachePut(value = Keys.CACHE_USER, key = "#result.username", condition = "#result != null")
    )
    public UserV getById(String userId) {

        User user = userRepo.findById(userId).orElse(null);

        return ObjectConvertTool.convert(user, UserV.class);
    }

    /**
     * 通过用户名获取用户
     *
     * @param username
     * @return
     */
    @Caching(
            cacheable = @Cacheable(value = Keys.CACHE_USER, key = "#username", unless = "#result == null"),
            put = @CachePut(value = Keys.CACHE_USER, key = "#result.userId", condition = "#result != null ")
    )
    public UserV getByUsername(String username) {
        User user = userRepo.findByUsername(username);
        return ObjectConvertTool.convert(user, UserV.class);
    }

    /**
     * 登录
     *
     * @param request
     * @return
     */
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = Keys.CACHE_USER, key = "#result.userId"),
                    @CacheEvict(value = Keys.CACHE_USER, key = "#request.username")
            }
    )
    public Login.Response login(Login.Request request) {

        User user = userRepo.findByUsername(request.getUsername());

        BizAssert.notNull(user, BizException.notFound("用户不存在"));

        String ip = request.getIp();
        String whiteList = user.getIpWhiteList();
        if (whiteList == null) whiteList = "";

        if (user.getIpTableEnable() && !whiteList.contains(ip)) {
            throw BizException.fail("用户IP[" + ip + "]禁止访问");
        }

        //检查用户状态
        BizAssert.isTrue(user.getEnable(), BizException.fail("用户已经被禁用"));

        //检查谷歌验证
        if (user.getGoogleAuthenticationEnabled()) {
            String gauthCode = request.getGauthCode();

            if (gauthCode == null || StringUtils.isEmpty(gauthCode)) {
                throw new BizException(RInfos.FAIL, "请输入谷歌验证码");
            }

            String gAuthCodeSecret = user.getGoogleAuthenticationSecret();
            boolean success = googleAuthenticator.check_code(gAuthCodeSecret, Long.parseLong(gauthCode), System.currentTimeMillis());

            BizAssert.isTrue(success, BizException.fail("谷歌验证码错误"));
        }

        //检查密码
        BizAssert.isTrue(passwordHelper.checkPassword(request.getPwd(), user.getPwdSalt(), user.getPwd()), BizException.fail("密码错误"));


        user.setLastLoginIp(request.getIp());
        user.setLastLoginTime(new Date());

        userRepo.save(user);

        //生成token
        String token = TokenUtil.generateToken();

        long effectiveTime = systemConfigs.loginEffectiveTime.get();

        //有效时间
        long expireTime = System.currentTimeMillis() + effectiveTime * 1000;

        redisService.setCacheObject(
                TokenUtil.getRedisTokenKey(token), user.getUserId(), effectiveTime, TimeUnit.SECONDS
        );

        //通过userid获取token可以实现单点登录
        redisService.setCacheObject(TokenUtil.getRedisTokenOfUser(user.getUserId()), token, effectiveTime, TimeUnit.SECONDS);

        log.info("==> 用户[id:{}, username:{}]登录成功, token:{}, expireTime:{}", user.getUserId(), user.getUsername(), token, new SimpleDateFormat(DateFormats.DATE_TIME).format(expireTime));

        Login.Response response = new Login.Response();
        response.setUserId(user.getUserId());
        response.setExpireTime(expireTime);
        response.setToken(Base64Util.encodeBase64(token));


        return response;
    }

    /**
     * 添加用户
     *
     * @param param
     */
    @Transactional
    public void addUser(AddUser param) {

        UserType userType = UserType.getUserType(param.getUserType());
        if (userType == null) {
            throw new BizException(RInfos.BAD_PARAM, "用户类型错误");
        }

        User user = userRepo.findByUsername(param.getUsername());
        if (user != null) {
            throw new BizException(RInfos.RESOURCE_ALREADY_EXSITS, "用户已存在");
        }



        user = new User();


        user.setUserId(idGenerator.generateId().longValue() + "");
        user.setUsername(param.getUsername());

        String pwdSalt = passwordHelper.generatePasswordSalt();
        String pwd = passwordHelper.calcPassword(param.getPwd(), pwdSalt);

        user.setPwd(pwd);
        user.setPwdSalt(pwdSalt);
        user.setUserType(userType.getValue());
        user.setGoogleAuthenticationEnabled(false);
        user.setSecret(passwordHelper.generateSecret(param.getUsername(), pwd, pwdSalt));


        user = userRepo.saveAndFlush(user);
    }

    /**
     * 更新用户
     *
     * @param
     * @return username
     */
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = Keys.CACHE_USER, key = "#param.userId"),
                    @CacheEvict(value = Keys.CACHE_USER, key = "#result"),
            }
    )
    public String updateUser(UpdateUser param) {

        User user = userRepo.findById(param.getUserId()).orElseThrow(() -> new BizException(RInfos.NOT_FOUND, "用户不存在"));

        //修改密码
        if (!StringUtils.isBlank(param.getPwd())) {
            String pwdSalt = passwordHelper.generatePasswordSalt();
            String pwd = passwordHelper.calcPassword(param.getPwd(), pwdSalt);
            user.setPwd(pwd);
            user.setPwdSalt(pwdSalt);
        }

        //修改二级密码
        if (!StringUtils.isBlank(param.getSpwd())) {
            String spwdSalt = passwordHelper.generatePasswordSalt();
            String spwd = passwordHelper.calcPassword(param.getSpwd(), spwdSalt);
            user.setSpwdSalt(spwdSalt);
            user.setSpwd(spwd);
        }

        //修改ip白名单
        if (!StringUtils.isBlank(param.getIpWhiteList())) {
            user.setIpWhiteList(param.getIpWhiteList());
        }

        //修改ip防火墙是否启用
        if (param.getIpTableEnable() != null) {
            user.setIpTableEnable(param.getIpTableEnable());
        }


        if (param.getEnable() != null) {
            user.setEnable(param.getEnable());
        }

        if (param.getGoogleAuthenticationEnabled() != null) {
            if (param.getGoogleAuthenticationEnabled()) throw new BizException(RInfos.BAD_PARAM, "谷歌验证码只可关闭");
            user.setGoogleAuthenticationEnabled(param.getGoogleAuthenticationEnabled());
        }


        userRepo.save(user);

        return user.getUsername();
    }

    /**
     * 启用谷歌验证码
     *
     * @param userId
     * @return
     */
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = Keys.CACHE_USER, key = "#result.userId"),
                    @CacheEvict(value = Keys.CACHE_USER, key = "#result.username"),
            }
    )
    public UserV enableGauth(String userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> BizException.notFound("用户不存在"));

        BizAssert.isNotTrue(user.getGoogleAuthenticationEnabled(), "谷歌验证码已经启用");

        //生成密钥
        String secret = GoogleAuthenticator.generateSecretKey();

        user.setGoogleAuthenticationEnabled(true);
        user.setGoogleAuthenticationSecret(secret);
        user = userRepo.saveAndFlush(user);

        return ObjectConvertTool.convert(user, UserV.class);
    }

    @Transactional
    @Caching(
            evict = {
            @CacheEvict(value = Keys.CACHE_USER, key = "#result.userId"),
            @CacheEvict(value = Keys.CACHE_USER, key = "#result.username"),
    })
    public UserV resetSecret(String userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> BizException.notFound("用户不存在"));

        String secret = passwordHelper.generateSecret(user.getUsername(), user.getPwd(), user.getPwdSalt());

        user.setSecret(secret);

        user = userRepo.saveAndFlush(user);

        return ObjectConvertTool.convert(user, UserV.class);
    }


}
