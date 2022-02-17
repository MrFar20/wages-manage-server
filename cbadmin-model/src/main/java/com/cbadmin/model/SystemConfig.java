package com.cbadmin.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 系统信息
 */
@Data
public class SystemConfig implements Serializable {

    private static final long serialVersionUID = 6567743753567417719L;

    /**
     * 侧边栏标题
     */
    private String sideBarTitle;

    /**
     * 系统登录名
     */
    private String systemLoginName;

    /**
     * 系统名
     */
    private String systemName;


    /**
     * 导出类型
     */
    private List<LabelValue<String>> exportType;
}
