package com.cbadmin.common.systemconfig;

import com.cbmai.core.systemconfig.types.StringConfig;
import org.springframework.stereotype.Component;

@Component
public class SystemName extends StringConfig {

    public static final String CONFIG_NAME = "system.name";

    @Override
    public String configName() {
        return CONFIG_NAME;
    }

    @Override
    public String description() {
        return "系统名";
    }

    @Override
    public String defaultValue() {
        return "后台管理";
    }
}
