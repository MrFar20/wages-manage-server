package com.cbadmin.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 */
@Data
public class UserV implements Serializable {

    private static final long serialVersionUID = 6870225268082400888L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后次登录ip
     */
    private String lastLoginIp;


    /**
     * 用户帐号 全局唯一
     */
    private String username;


    /**
     * 密码
     * 前端密码通过md5加密
     */
    private String pwd;

    /**
     * 密码盐
     */
    private String pwdSalt;

    /**
     * 二级密码
     * 前端密码通过md5加密
     */
    private String spwd;

    /**
     * 二级密码盐
     */
    private String spwdSalt;

    /**
     * 谷歌验证是否开启
     */
    private Boolean googleAuthenticationEnabled;

    /**
     * 谷歌验证码 md5 加密
     */
    private String googleAuthenticationSecret;

    /**
     * 密钥
     */
    private String secret;

    /**
     * ip白名单
     */
    private String ipWhiteList;

    /**
     * ip防火墙开启
     */
    private Boolean ipTableEnable;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 是否已经登录
     */
    private Boolean hasLogin;
}
