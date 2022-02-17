package com.cbadmin.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StorageV implements Serializable {

    private static final long serialVersionUID = -1488899417880503580L;


    /**
     * 存储Id
     */
    private String id;


    /**
     * 类型
     */
    private String type;

    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 用户id
     */
    private String userId;


    /**
     * 用户名
     */
    private String username;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件全路径
     */
    private String fileUrl;

    /**
     * 文件类型（图片、文档、等）
     */
    private String fileType;

    /**
     * 文件后缀（jpg|zip|....）
     */
    private String fileSuffix;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 备注
     */
    private String remark;

}
