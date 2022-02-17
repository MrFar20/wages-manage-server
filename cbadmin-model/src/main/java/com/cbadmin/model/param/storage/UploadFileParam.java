package com.cbadmin.model.param.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadFileParam implements Serializable {

    private static final long serialVersionUID = 8706528112290108965L;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件字节流
     */
    private byte[] bytes;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作人用户名
     */
    private String username;

    /**
     * 用户id
     */
    private String userId;

}
