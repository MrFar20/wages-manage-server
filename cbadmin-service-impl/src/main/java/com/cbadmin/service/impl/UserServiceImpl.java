package com.cbadmin.service.impl;

import com.cbadmin.common.util.TokenUtil;
import com.cbadmin.dao.entity.User;
import com.cbadmin.dao.repo.UserRepo;
import com.cbadmin.model.param.user.AddUser;
import com.cbadmin.model.param.user.Check;
import com.cbadmin.model.param.user.Login;
import com.cbadmin.model.param.user.UpdateUser;
import com.cbadmin.model.vo.UserV;
import com.cbadmin.service.UserService;
import com.cbadmin.service.impl.cache.UserCacheService;
import com.cbmai.common.exception.BizException;
import com.cbmai.common.query.QueryCondition;
import com.cbmai.common.result.R;
import com.cbmai.common.util.BizAssert;
import com.cbmai.core.converter.ObjectConvertTool;
import com.cbmai.core.gauth.GoogleAuthenticator;
import com.cbmai.core.password.PasswordHelper;
import com.cbmai.redis.service.RedisService;
import com.cbmai.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Service
@Slf4j
public class UserServiceImpl extends BaseService<UserRepo> implements UserService {

    public static void main(String[] args) {
        System.out.println(String.format("and %s like %%:%s%%", "name", "name"));
    }

    @Autowired
    private UserCacheService userCacheService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private PasswordHelper passwordHelper;

    @Autowired
    private GoogleAuthenticator googleAuthenticator;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public R query(QueryCondition queryCondition) {

        R r = super.query(queryCondition);
        r.setData(
                ObjectConvertTool.convert((List<User>) r.getData(), UserV.class)
        );

        //是否已经登录
        List<UserV> users = (List<UserV>) r.getData();
        users.forEach(user -> {
            if (redisService.getCacheObject(
                    TokenUtil.getRedisTokenOfUser(user.getUserId())
            ) != null) {
                user.setHasLogin(true);
            } else {
                user.setHasLogin(false);
            }
        });


        return r;
    }

    @Override
    public UserV getById(String userId) {
        return userCacheService.getById(userId);
    }

    @Override
    public UserV getByUsername(String username) {
        return userCacheService.getByUsername(username);
    }

    @Override
    public Login.Response login(Login.Request request) {
        return userCacheService.login(request);
    }

    @Override
    public void loginOut(String token) {

        String userIdKey = TokenUtil.getRedisTokenKey(token);

        String userId = redisService.getCacheObject(userIdKey);

        if (userId != null) {
            redisService.deleteObject(userIdKey);
            redisService.deleteObject(TokenUtil.getRedisTokenOfUser(userId));
        }

    }

    @Override
    public void loginOutByUserId(String userId) {

        String tokenOfUserKey = TokenUtil.getRedisTokenOfUser(userId);

        String token = redisService.getCacheObject(tokenOfUserKey);

        if (token != null) {
            redisService.deleteObject(tokenOfUserKey);
            redisService.deleteObject(TokenUtil.getRedisTokenKey(token));
        }

    }

    @Override
    public void checkPwd(Check.Password param) {
        UserV user = userCacheService.getById(param.getUserId());

        BizAssert.notNull(user, BizException.notFound("用户不存在"));

        BizAssert.isTrue(passwordHelper.checkPassword(param.getPwd(), user.getPwdSalt(), user.getPwd()), "密码错误");
    }

    @Override
    public void checkSpwd(Check.Password param) {
        UserV user = userCacheService.getById(param.getUserId());

        BizAssert.notNull(user, BizException.notFound("用户不存在"));

        BizAssert.isTrue(passwordHelper.checkPassword(param.getPwd(), user.getSpwdSalt(), user.getSpwd()), "二级密码错误");
    }

    @Override
    public boolean checkGauth(Check.Gauth param) {

        UserV user = userCacheService.getById(param.getUserId());

        BizAssert.notNull(user, BizException.notFound("用户不存在"));

        if (!user.getGoogleAuthenticationEnabled()) return true;

        Long code = null;

        try {
            code = Long.parseLong(param.getGauthCode());
        } catch (Exception e) {
            return false;
        }

        return googleAuthenticator.check_code(user.getGoogleAuthenticationSecret(), code, System.currentTimeMillis());
    }

    @Override
    public void checkVerifyCode(String userId, String code) {

        UserV user = userCacheService.getById(userId);

        BizAssert.notNull(user, BizException.notFound("用户不存在"));


        if (user.getGoogleAuthenticationEnabled()) {

            Check.Gauth gauth = new Check.Gauth();
            gauth.setUserId(userId);
            gauth.setGauthCode(code);
            BizAssert.isTrue(checkGauth(gauth), "谷歌验证码错误");

        } else {

            Check.Password password = new Check.Password();
            password.setUserId(userId);
            password.setPwd(code);

            checkSpwd(password);
        }

    }

    @Override
    public void addUser(AddUser param) {
        userCacheService.addUser(param);
    }

    @Override
    public void updateUser(UpdateUser param) {
        userCacheService.updateUser(param);
    }

    @Override
    public String enableGauth(String userId) {
        return userCacheService.enableGauth(userId).getGoogleAuthenticationSecret();
    }

    @Override
    public void resetSecret(String userId) {
        userCacheService.resetSecret(userId);
    }
}
