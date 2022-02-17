package com.cbadmin.common.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

public class EnvUtils implements InitializingBean {

    private static boolean isDev = false;

    @Value("${spring.profiles.active}")
    private String env;


    /**
     * 是否为开发环境
     * @return
     */
    public static boolean isDev() {
        return isDev;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        isDev =  "dev".equals(env);
    }
}
