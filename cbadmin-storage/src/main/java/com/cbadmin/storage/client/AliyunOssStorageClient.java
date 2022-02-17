package com.cbadmin.storage.client;

import com.aliyun.oss.OSS;
import com.cbadmin.common.constant.Keys;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @createTime: 2021-03-04 11:14:39
 * @description: 阿里云oss的实现
 */
@Slf4j
public class AliyunOssStorageClient implements StorageClient {

    private final OSS oss;
    private final String endpoint;
    private final String bucketName;
    private final String accessUrl;

    public AliyunOssStorageClient(OSS oss, String endpoint, String accessUrl, String bucketName) {
        this.oss = oss;
        this.endpoint = endpoint;
        this.accessUrl = accessUrl;
        this.bucketName = bucketName;
    }

    @Override
    public String type() {
        return Keys.STORAGE_TYPE_ALIYUNOSS;
    }

    @Override
    public String uploadFile(String filePath, String fileName, byte[] bytes) {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        this.oss.putObject(bucketName, getStoragePath(filePath) + fileName, inputStream);
        return getFileFullPath(filePath, fileName);
    }

    @Override
    public String uploadFile( String filePath, String fileName, InputStream inputStream) {
        this.oss.putObject(bucketName, getStoragePath(filePath) + fileName, inputStream);
        return getFileFullPath( filePath, fileName);
    }

    /**
     * 获取文件的全路径
     * @param filePath      存储路径
     * @param fileName      文件名称
     * @return
     */
    @Override
    public String getFileFullPath(String filePath, String fileName) {
        return String.format("https://%s/%s", this.accessUrl, getStoragePath(filePath) + fileName);
        // return "https://" + bucketName + "." + this.endpoint + "/" + getStoragePath(filePath) + fileName;
    }

    /**
     * 文件存储目录
     *
     * @param filePath 存储路径
     * @return String
     */
    @Override
    public String getStoragePath(String filePath) {
        String separator = "/";
        if (filePath.endsWith(separator)) {
            return filePath;
        } else {
            return filePath + separator;
        }
    }



}
