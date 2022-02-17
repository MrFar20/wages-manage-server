package com.cbadmin.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @createTime: 2021-08-17 13:56:35
 * @created by: mrwangx
 * @description: 用户
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", indexes = {
        @Index(columnList = "username", unique = true),
        @Index(columnList = "userType"),
        @Index(columnList = "enable"),
})
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {


    private static final long serialVersionUID = -3014069470692653510L;

    /**
     * 用户id
     */
    @Id
    @Column(columnDefinition = "varchar(32)")
    private String userId;

    /**
     * 用户类型
     */
    @Column(columnDefinition = "varchar(12) not null comment '用户类型'")
    private String userType;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(columnDefinition = "datetime(3) not null comment '创建时间'")
    private Date createTime;

    /**
     * 更新时间
     */
    @LastModifiedDate
    @Column(columnDefinition = "datetime(3) not null comment '更新时间'")
    private Date updateTime;

    /**
     * 上次登录时间
     */
    @Column(columnDefinition = "datetime(3) comment '最后次登录时间'")
    private Date lastLoginTime;

    /**
     * 最后次登录ip
     */
    @Column(columnDefinition = "varchar(48) comment '最后次登录ip'")
    private String lastLoginIp;


    /**
     * 用户帐号 全局唯一
     */
    @Column(columnDefinition = "varchar(32) not null comment '用户帐号'", unique = true)
    private String username;

    /**
     * 密码
     * 前端密码通过md5加密
     */
    @Column(columnDefinition = "varchar(60) not null comment '密码, 默认admin123456'")
    private String pwd;

    /**
     * 密码盐
     */
    @Column(columnDefinition = "varchar(32) not null comment '密码盐'")
    private String pwdSalt;

    /**
     * 二级密码
     * 前端密码通过md5加密
     */
    @Column(columnDefinition = "varchar(60) not null default '$2a$10$FmMou5sxAMSGRaHXCSgdSeyQKGLFZj/8Nxv59sWxqaT9YeK2QQ2IW' comment '二级密码, 默认123456'")
    private String spwd;

    /**
     * 二级密码盐
     */
    @Column(columnDefinition = "varchar(32) not null default '$2a$10$FmMou5sxAMSGRaHXCSgdSe' comment '二级密码盐'")
    private String spwdSalt;

    /**
     * 谷歌验证是否开启
     */
    @Column(columnDefinition = "tinyint not null default 0 comment '谷歌验证是否开启'")
    private Boolean googleAuthenticationEnabled;

    /**
     * 谷歌验证码 md5 加密
     */
    @Column(columnDefinition = "varchar(16) comment '谷歌验证码密钥'")
    private String googleAuthenticationSecret;

    /**
     * 密钥
     */
    @Column(columnDefinition = "varchar(32) not null comment '密钥'")
    private String secret;

    /**
     * ip白名单
     */
    @Column(columnDefinition = "text comment 'ip白名单'")
    private String ipWhiteList;

    /**
     * ip防火墙开启
     */
    @Column(columnDefinition = "tinyint not null default 0 comment 'ip防火墙是否开启'")
    private Boolean ipTableEnable;

    /**
     * 是否启用
     */
    @Column(columnDefinition = "tinyint not null default 1 comment '是否启用'")
    private Boolean enable;

}
