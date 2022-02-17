package com.cbadmin.common.systemconfig;

import com.cbmai.core.systemconfig.types.IntConfig;
import org.springframework.stereotype.Component;

/**
 * 登录有效时间(秒)
 */
@Component
public class LoginEffectiveTime extends IntConfig {

    public static final String CONFIG_NAME = "login-effective-time";

    @Override
    public String configName() {
        return CONFIG_NAME;
    }

    @Override
    public String description() {
        return "登录有效时间(秒)";
    }

    @Override
    public Long defaultValue() {
        return 24 * 60 * 60l;
    }
}
