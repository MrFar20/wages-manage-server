package com.cbadmin.storage.client;

import java.io.InputStream;

/**
 *
 * @createTime: 2021-03-10 11:08:47
 * @description:
 */
public interface StorageClient {

    /**
     * 类型
     * @return
     */
    String type();

    /**
     * 文件上传
     *
     * @param filePath 存储路径
     * @param fileName 文件名称 (带后缀的文件名称)
     * @param bytes    文件的二进制数组
     * @return
     */
    String uploadFile(String filePath, String fileName, byte[] bytes);

    /**
     * 文件上传
     *
     * @param filePath    存储路径
     * @param fileName    文件名称 (带后缀的文件名称)
     * @param inputStream 文件流
     * @return
     */
    String uploadFile(String filePath, String fileName, InputStream inputStream);

    /**
     * 获取文件的全路径
     *
     * @param filePath 存储路径
     * @param fileName 文件名称 (带后缀的文件名称)
     * @return
     */
    String getFileFullPath(String filePath, String fileName);

    /**
     * 文件存储目录
     *
     * @param filePath 存储路径
     * @return String
     */
    String getStoragePath(String filePath);


}
