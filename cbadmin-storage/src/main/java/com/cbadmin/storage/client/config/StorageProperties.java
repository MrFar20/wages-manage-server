package com.cbadmin.storage.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 *
 * @createTime: 2021-03-04 11:08:20
 * @description:
 */
@Data
@ConfigurationProperties(prefix = "system.storage")
public class StorageProperties implements Serializable {


    private static final long serialVersionUID = 1810810931731290695L;

    /**
     * 存储方式(aliyunoss|local)
     */
    private String type;

    /**
     * 本地存储配置
     */
    private Local local;

    /**
     * 阿里云配置
     */
    private Aliyun aliyun;


    /**
     * 本地配置
     */
    @Data
    public static class Local {

        /**
         * 本地存储路径
         */
        private String root;

    }


    /**
     * 阿里云配置
     */
    @Data
    public static class Aliyun {

        /**
         * 外网访问url前缀
         */
        private String accessUrl;


        /**
         * 云存储空间名称
         */
        private String bucketName;

        /**
         * 端点
         */
        private String endpoint;

        /**
         * 访问身份验证用户标识
         */
        private String accessKeyId;

        /**
         * 访问身份验证用户密钥
         */
        private String accessKeySecret;

    }

}
