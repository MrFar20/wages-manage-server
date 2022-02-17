package com.cbadmin.service;


import com.cbadmin.model.param.storage.UploadFileParam;
import com.cbadmin.model.vo.StorageV;
import com.cbmai.common.service.DataQueryService;

import java.util.List;

/**
 * 存储服务
 * @createTime: 2021-03-08 15:07:36
 * @description:
 */
public interface StorageService extends DataQueryService {

    /**
     * 上传文件
     * @param param
     * @return 返回文件ID
     */
    StorageV uploadFile(UploadFileParam param);


    /**
     * 存储服务
     * @param storageId
     * @return
     */
    StorageV getById(String storageId);


    /**
     * 获取存储
     * @param storageIds
     * @return
     */
    List<StorageV> getByIds(List<String> storageIds);


    /**
     * 删除storage
     * @param storageId
     */
    void deleteStorageById(String storageId);

}
