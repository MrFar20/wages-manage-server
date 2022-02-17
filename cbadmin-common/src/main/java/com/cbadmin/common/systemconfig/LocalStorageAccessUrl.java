package com.cbadmin.common.systemconfig;

import com.cbmai.core.systemconfig.types.StringConfig;
import org.springframework.stereotype.Component;

@Component
public class LocalStorageAccessUrl extends StringConfig {

    public static final String CONFIG_NAME = "localstorage.accessurl";

    @Override
    public String configName() {
        return CONFIG_NAME;
    }

    @Override
    public String description() {
        return "本地存储文件访问前缀";
    }

    @Override
    public String defaultValue() {
        return "http://127.0.0.1";
    }
}
