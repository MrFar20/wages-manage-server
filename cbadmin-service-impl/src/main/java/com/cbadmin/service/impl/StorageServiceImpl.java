package com.cbadmin.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.cbadmin.dao.entity.Storage;
import com.cbadmin.dao.repo.StorageRepo;
import com.cbadmin.model.param.storage.UploadFileParam;
import com.cbadmin.model.vo.StorageV;
import com.cbadmin.service.StorageService;
import com.cbadmin.service.impl.cache.StorageCacheService;
import com.cbadmin.storage.client.StorageClient;
import com.cbmai.common.query.QueryCondition;
import com.cbmai.common.result.R;
import com.cbmai.core.converter.ObjectConvertTool;
import com.cbmai.core.util.FileUtil;
import com.cbmai.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 存储服务
 */
@Service
public class StorageServiceImpl extends BaseService<StorageRepo> implements StorageService {

    @Autowired
    private StorageCacheService storageCacheService;

    @Autowired
    private StorageClient storageClient;

    /**
     * 上传文件
     * @param param
     * @return
     */
    @Override
    @Transactional
    public StorageV uploadFile(UploadFileParam param) {

        String fileRealName = param.getFileName();
        String remark = param.getRemark();
        String username = param.getUsername();
        String userId = param.getUserId();
        byte[] fileBytes = param.getBytes();

        String uuid = IdUtil.fastSimpleUUID();
        String filePath = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);

        String suffix = FileUtil.getExtensionName(fileRealName);
        String fileType = FileUtil.getFileType(suffix);

        // 上传文件
        String fileName = uuid + "." + suffix;
        String fileUrl = storageClient.uploadFile(filePath, fileName, fileBytes);

        long length = fileBytes.length;

        Storage storage = Storage.builder()
                .id(fileName)
                .type(storageClient.type())
                .fileName(fileRealName)
                .filePath(filePath)
                .fileSize(length)
                .fileUrl(fileUrl)
                .fileSuffix(suffix)
                .fileType(fileType)
                .remark(remark)
                .username(username)
                .userId(userId)
                .build();

        storage = this.entityRepo.saveAndFlush(storage);


        return ObjectConvertTool.convert(storage, StorageV.class);
    }

    @Override
    public StorageV getById(String storageId) {
        return storageCacheService.getById(storageId);
    }

    @Override
    public List<StorageV> getByIds(List<String> storageIds) {
        return ObjectConvertTool.convert(
                entityRepo.findByIdIn(storageIds), StorageV.class
        );
    }

    @Override
    public void deleteStorageById(String storageId) {
        storageCacheService.deleteStorageById(storageId);
    }

    @Override
    public R<?> query(QueryCondition queryCondition) {
        R r = super.query(queryCondition);

        r.setData(
             ObjectConvertTool.convert((List<Storage>) r.getData(), StorageV.class)
        );

        return r;
    }
}
