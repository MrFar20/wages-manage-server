package com.cbadmin.common.component;

import com.cbadmin.common.constant.Constants;
import com.cbmai.common.constant.Constant;
import org.springframework.stereotype.Component;

@Component
public class CbConstant implements Constant {

    //redis 配置项的key
    public static final String REDIS_HASH_KEY_CONFIG = Constants.KEYS_PREFIX + ":systemconfig";

    //ws session key值的key
    public static final String WEB_SOCKET_SESSION_KEY = Constants.KEYS_PREFIX + ":session:key:ws";

    //session里用户基本信息的key
    public static final String SESSION_KEY_USERINFO = Constants.KEYS_PREFIX + "-session-key-userinfo";

    //发送订阅信息的前缀
    public static final String PREFIX_KEY_REDIS_MESSAGE_CHANNEL = Constants.KEYS_PREFIX + "-redis-channel-message:";

    public static final String PREFIX_KEY_REDIS_MESSAGE_QUEUE = Constants.KEYS_PREFIX + "-redis-message:";

    @Override
    public String redisConfigHashKey() {
        return REDIS_HASH_KEY_CONFIG;
    }

    @Override
    public String webSocketSessionKey() {
        return WEB_SOCKET_SESSION_KEY;
    }

    @Override
    public String sessionKeyUserinfo() {
        return SESSION_KEY_USERINFO;
    }

    @Override
    public String prefixKeyRedisMessageChannel() {
        return PREFIX_KEY_REDIS_MESSAGE_CHANNEL;
    }

    @Override
    public String prefixKeyRedisMessageQueue() {
        return PREFIX_KEY_REDIS_MESSAGE_QUEUE;
    }
}
