package com.cbadmin.service.impl.cache;

import com.cbadmin.common.constant.Keys;
import com.cbadmin.dao.entity.Storage;
import com.cbadmin.dao.repo.StorageRepo;
import com.cbadmin.model.vo.StorageV;
import com.cbmai.core.converter.ObjectConvertTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StorageCacheService {

    @Autowired
    private StorageRepo storageRepo;


    @Transactional(readOnly = true)
    @Cacheable(value = Keys.CACHE_STORAGE, key = "#storageId", unless = " #result == null ")
    public StorageV getById(String storageId) {
        Storage storage = storageRepo.findById(storageId).orElse(null);
        return ObjectConvertTool.convert(storage, StorageV.class);
    }


    /**
     * @param storageId
     */
    @Transactional
    @CacheEvict(value = Keys.CACHE_STORAGE, key = "#storageId")
    public void deleteStorageById(String storageId) {
        storageRepo._deleteById(storageId);
    }

}
