package com.cbadmin.storage.client;

import cn.hutool.core.io.FileUtil;
import com.cbadmin.common.constant.Keys;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 *
 * @createTime: 2021-03-10 11:22:51
 * @description: 本地存储的实现
 */
@Slf4j
public class LocalStorageClient implements StorageClient {

    private final String root;

    public LocalStorageClient(String root) {
        this.root = root;
    }

    @Override
    public String type() {
        return Keys.STORAGE_TYPE_LOCAL;
    }

    @Override
    public String uploadFile(String filePath, String fileName, byte[] bytes) {
        String path = getFileFullPath(filePath, fileName);
        FileUtil.writeBytes(bytes, path);
        return path;
    }

    @Override
    public String uploadFile(String filePath, String fileName, InputStream inputStream) {
        String path = getFileFullPath(filePath, fileName);
        FileUtil.writeFromStream(inputStream, path);
        return path;
    }

    @Override
    public String getFileFullPath(String filePath, String fileName) {
        return getStoragePath(root) + getStoragePath(filePath) + fileName;
    }

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
