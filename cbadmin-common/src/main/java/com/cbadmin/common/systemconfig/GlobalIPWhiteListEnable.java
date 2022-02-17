package com.cbadmin.common.systemconfig;

import com.cbmai.core.systemconfig.types.BoolConfig;
import org.springframework.stereotype.Component;

@Component
public class GlobalIPWhiteListEnable extends BoolConfig {

    public static final String CONFIG_NAME = "global-ip-white-list-enable";

    @Override
    public String configName() {
        return CONFIG_NAME;
    }

    @Override
    public String description() {
        return "全局ip白名单启用";
    }

    @Override
    public Boolean defaultValue() {
        return false;
    }
}
