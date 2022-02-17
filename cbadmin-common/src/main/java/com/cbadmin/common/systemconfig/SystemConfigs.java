package com.cbadmin.common.systemconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemConfigs {

    /**
     * 登录有效时间(秒)
     */
    @Autowired
    public LoginEffectiveTime loginEffectiveTime;

    /**
     * 全局ip白名单
     */
    @Autowired
    public GlobalIPWhiteList globalIPWhiteList;

    /**
     * 全局ip白名单启用
     */
    @Autowired
    public GlobalIPWhiteListEnable globalIPWhiteListEnable;

    /**
     * 系统登录名
     */
    @Autowired
    public SystemLoginName systemLoginName;

    /**
     * 系统名
     */
    @Autowired
    public SystemName systemName;

    /**
     * 侧边栏标题
     */
    @Autowired
    public SideBarTitle sideBarTitle;
}



