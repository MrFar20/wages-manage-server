package com.cbadmin.storage.client.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.cbadmin.common.constant.Keys;
import com.cbadmin.storage.client.AliyunOssStorageClient;
import com.cbadmin.storage.client.LocalStorageClient;
import com.cbadmin.storage.client.StorageClient;
import com.cbmai.common.exception.BizException;
import com.cbmai.common.util.BizAssert;
import com.cbmai.core.util.FileUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(StorageProperties.class)
public class StorageBeanConfig {

    /**
     * 存储client
     * @return
     */
    @Bean
    public StorageClient storageClient(StorageProperties storageProperties) {

        //判断存储client的类型 生成不同的storageClient
        switch (storageProperties.getType()) {

            //本地
            case Keys.STORAGE_TYPE_LOCAL: {

                StorageProperties.Local config = storageProperties.getLocal();

                String rootPath = config.getRoot();

                //文件夹不存在
                BizAssert.isTrue(FileUtil.exist(rootPath) && FileUtil.isDirectory(rootPath), BizException.notFound("文件夹[" + rootPath + "]不存在"));


                return new LocalStorageClient(config.getRoot());

            }

            //阿里云OSS
            case Keys.STORAGE_TYPE_ALIYUNOSS: {

                StorageProperties.Aliyun aliyun = storageProperties.getAliyun();

                //端点
                String endpoint = aliyun.getEndpoint();

                String bucketName = aliyun.getBucketName();


                String accessUrl = aliyun.getAccessUrl();

                OSS oss = new OSSClientBuilder().build(endpoint, aliyun.getAccessKeyId(), aliyun.getAccessKeySecret());

                return new AliyunOssStorageClient(oss, endpoint, accessUrl, bucketName);

            }

            default:
                throw BizException.notFound("不存在的存储类型:" + storageProperties.getType());
        }
    }

}
