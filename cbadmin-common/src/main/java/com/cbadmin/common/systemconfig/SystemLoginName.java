package com.cbadmin.common.systemconfig;

import com.cbmai.core.systemconfig.types.StringConfig;
import org.springframework.stereotype.Component;

@Component
public class SystemLoginName extends StringConfig {

    public static final String CONFIG_NAME = "system.login.name";

    @Override
    public String configName() {
        return CONFIG_NAME;
    }

    @Override
    public String description() {
        return "系统登录名";
    }

    @Override
    public String defaultValue() {
        return "后台管理登录";
    }
}
