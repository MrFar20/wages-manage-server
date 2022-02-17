package com.cbadmin.service;

import com.cbadmin.model.SystemConfig;

/**
 * 系统服务
 */
public interface SystemService {

    /**
     * 初始化系统
     */
    //初始化
    void init();


    /**
     * 系统配置信息
     * @return
     */
    SystemConfig systemConfig();

}
