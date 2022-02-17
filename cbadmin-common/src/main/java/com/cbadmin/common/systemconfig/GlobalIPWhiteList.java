package com.cbadmin.common.systemconfig;

import com.cbmai.core.systemconfig.types.StringConfig;
import org.springframework.stereotype.Component;

@Component
public class GlobalIPWhiteList extends StringConfig {

    public static final String CONFIG_NAME = "global-iptables";

    @Override
    public String configName() {
        return CONFIG_NAME;
    }

    @Override
    public String description() {
        return "全局ip白名单";
    }

    @Override
    public String defaultValue() {
        return "";
    }
}
