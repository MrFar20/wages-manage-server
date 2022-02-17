package com.cbadmin.common.util;

import com.cbadmin.common.constant.Keys;

import java.util.UUID;

public class TokenUtil {

    private TokenUtil() {}

    /**
     * 生成token
     * @return
     */
    public static String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 获取redis token的key
     * @param token
     * @return
     */
    public static String getRedisTokenKey(String token) {
        return Keys.REDIS_PREFIX_TOKEN + token;
    }

    /**
     * 获取用户
     * @param userId
     * @return
     */
    public static String getRedisTokenOfUser(String userId) {
        return Keys.REDIS_PREFIX_TOKEN_OF_USER + userId;
    }

}

