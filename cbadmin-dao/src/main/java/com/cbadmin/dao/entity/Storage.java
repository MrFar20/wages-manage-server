package com.cbadmin.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 存储
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "storage", indexes = {
        @Index(columnList = "username"),
        @Index(columnList = "userId")
})
@DynamicUpdate
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class Storage implements Serializable {

    private static final long serialVersionUID = 2338003526641211268L;

    /**
     * 存储Id
     */
    @Id
    @Column(columnDefinition = "varchar(64)")
    private String id;


    /**
     * 创建时间
     */
    @CreatedDate
    @Column(columnDefinition = "datetime not null comment '创建时间'")
    private Date createTime;


    /**
     * 存储类型
     */
    @Column(columnDefinition = "varchar(12) not null comment '存储类型'")
    private String type;

    /**
     * 用户id
     */
    @Column(columnDefinition = "varchar(32) comment '用户id'")
    private String userId;


    /**
     * 用户名
     */
    @Column(columnDefinition = "varchar(32) comment '用户名'")
    private String username;

    /**
     * 文件名
     */
    @Column(columnDefinition = "varchar(100) not null comment '文件名'")
    private String fileName;

    /**
     * 文件路径
     */
    @Column(columnDefinition = "varchar(100) default null comment '文件路径'")
    private String filePath;

    /**
     * 文件全路径
     */
    @Column(columnDefinition = "varchar(100) default null comment '文件全路径'")
    private String fileUrl;

    /**
     * 文件类型（图片、文档、等）
     */
    @Column(columnDefinition = "varchar(100) default null comment '文件类型（图片、文档、等）'")
    private String fileType;

    /**
     * 文件后缀（jpg|zip|....）
     */
    @Column(columnDefinition = "varchar(100) default null comment '文件后缀（jpg|zip|....）'")
    private String fileSuffix;

    /**
     * 文件大小
     */
    @Column(columnDefinition = "bigint not null default 0 comment '文件大小'")
    private Long fileSize;

    /**
     * 备注
     */
    @Column(columnDefinition = "varchar(255) comment '备注'")
    private String remark;

}
