package com.cbadmin.web.service;


import com.cbadmin.common.constant.Keys;
import com.cbadmin.common.util.TokenUtil;
import com.cbadmin.model.vo.UserV;
import com.cbadmin.service.UserService;
import com.cbmai.common.exception.BizException;
import com.cbmai.common.result.RInfos;
import com.cbmai.common.util.Base64Util;
import com.cbmai.common.util.StringUtils;
import com.cbmai.redis.service.RedisService;
import com.cbmai.web.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 权限服务
 */
@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    /**
     * 单点登录
     */
    private boolean singlePointLogin = true;


    public UserV getUser() {
        Thread t = Thread.currentThread();
        synchronized (t) {
            UserV user = RequestUtils.requestAttr(Keys.WEB_REQUEST_ATTR_USER);
            if (user == null) {
                user = getUser(getRequestToken());
                RequestUtils.requestAttr(Keys.WEB_REQUEST_ATTR_USER, user);
            }
            return user;
        }
    }


    /**
     * 获取用户
     *
     * @param token
     * @return
     */
    public UserV getUser(String token) {

        String userId = getUserId(token);

        if (StringUtils.isNullOrBlank(userId)) {
            throw new BizException(RInfos.UNAUTHORIZED);
        }

        return userService.getById(userId);
    }


    /**
     * 根据该次请求的用户id
     *
     * @return
     */
    public String getUserId() {
        Thread t = Thread.currentThread();
        synchronized (t) {
            String userId = RequestUtils.requestAttr(Keys.WEB_REQUEST_ATTR_USER_ID);
            if (userId == null) {
                userId = getUserId(
                        getRequestToken()
                );
                RequestUtils.requestAttr(Keys.WEB_REQUEST_ATTR_USER_ID, userId);
            }
            return userId;
        }
    }

    /**
     * 根据token获取用户id
     *
     * @param token
     * @return
     */
    public String getUserId(String token) {

        String tokenKey = TokenUtil.getRedisTokenKey(token);

        String userId = redisService.getCacheObject(tokenKey);

        //单点登录
        if (singlePointLogin) {
            String tokenOfUser = getTokenOfUser(userId);
            if (!token.equals(tokenOfUser)) {
                //删除以前的token
                redisService.deleteObject(
                        TokenUtil.getRedisTokenKey(getRequestToken())
                );
                throw new BizException(RInfos.UNAUTHORIZED);
            }
        }

        return userId;
    }

    /**
     * 根据用户id获取token
     * @param userId
     * @return
     */
    public String getTokenOfUser(String userId) {
        String userTokenKey = TokenUtil.getRedisTokenOfUser(userId);
        return redisService.getCacheObject(userTokenKey);
    }


    /**
     * 获取请求头加密的token
     *
     * @return
     */
    public String getHeaderEntryptedToken() {
        return RequestUtils.servletRequest().getHeader(Keys.TOKEN_HEADER);
    }

    /**
     * 解密token
     *
     * @param headerToken
     * @param throwE      是否抛出异常
     * @return
     */
    public String decryptToken(String headerToken, boolean throwE) {
        try {
            //获取到aes token
            return Base64Util.decodeBase64ToString(headerToken);
        } catch (Exception e) {
            log.error("==> 获取token错误", e);
            if (throwE) {
                throw new BizException(RInfos.UNAUTHORIZED);
            }
        }
        return null;
    }

    /**
     * 获取请求的token
     *
     * @return
     */
    public String getRequestToken() {

        Thread t = Thread.currentThread();
        synchronized (t) {
            String token = RequestUtils.requestAttr(Keys.WEB_REQUEST_ATTR_USER_TOKEN);
            if (token == null) {
                //获取到base64的token
                String authBase64 = getHeaderEntryptedToken();
                if (StringUtils.isNullOrBlank(authBase64)) {
                    throw new BizException(RInfos.UNAUTHORIZED);
                }
                token = decryptToken(authBase64, true);
                RequestUtils.requestAttr(Keys.WEB_REQUEST_ATTR_USER_TOKEN, token);
            }
            return token;
        }

    }

}
