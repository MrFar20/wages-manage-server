package com.cbadmin.common.constant;

/**
 * 键值
 */
public class Keys {

    private Keys() {}

    //redis-stream
    /**
     * redis stream 组
     */
    public static final String REDIS_STREAM_GROUP = "ep-default";

    //缓存键值
    public static final String CACHE_USER = Constants.KEYS_PREFIX + ":user";
    public static final String CACHE_STORAGE = Constants.KEYS_PREFIX + ":storage";
    public static final String CACHE_WORKER = Constants.KEYS_PREFIX + ":worker";
    public static final String CACHE_CAMP_ALL  = Constants.KEYS_PREFIX + ":camp_all";


    //redis键
    public static final String REDIS_PREFIX_TOKEN = Constants.KEYS_PREFIX + ":token:";
    public static final String REDIS_PREFIX_TOKEN_OF_USER = Constants.KEYS_PREFIX + ":usertoken:";

    //token的header
    public static final String TOKEN_HEADER = "Authorization";

    //请求用户的attr
    public static final String WEB_REQUEST_ATTR_USER = Constants.KEYS_PREFIX + "-user";
    public static final String WEB_REQUEST_ATTR_USER_ID = Constants.KEYS_PREFIX + "-user-id";
    public static final String WEB_REQUEST_ATTR_USER_TOKEN = Constants.KEYS_PREFIX + "-user-token";


    //存储type
    public static final String STORAGE_TYPE_LOCAL = "local";
    public static final String STORAGE_TYPE_ALIYUNOSS = "aliyunoss";

}
