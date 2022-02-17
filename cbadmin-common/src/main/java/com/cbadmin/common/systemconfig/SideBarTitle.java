package com.cbadmin.common.systemconfig;

import com.cbmai.core.systemconfig.types.StringConfig;
import org.springframework.stereotype.Component;

/**
 * 侧边栏标题
 */
@Component
public class SideBarTitle extends StringConfig {

    public static final String CONFIG_NAME = "sidebar-title";

    @Override
    public String configName() {
        return CONFIG_NAME;
    }

    @Override
    public String description() {
        return "侧边栏标题";
    }

    @Override
    public String defaultValue() {
        return "CbAdmin";
    }
}
