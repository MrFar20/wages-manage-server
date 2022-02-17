package com.cbadmin.service.impl.cache;

import com.cbadmin.common.constant.Keys;
import com.cbadmin.dao.entity.Worker;
import com.cbadmin.dao.repo.WorkerRepo;
import com.cbadmin.model.param.worker.UpdateWorker;
import com.cbadmin.model.vo.WorkerV;
import com.cbmai.common.exception.BizException;
import com.cbmai.common.util.BizAssert;
import com.cbmai.common.util.ObjUtils;
import com.cbmai.core.converter.ObjectConvertTool;
import com.cbmai.core.param.DeleteByStringIds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkerCacheService  {

    @Autowired
    private WorkerRepo workerRepo;

    @Autowired
    private StorageCacheService storageCacheService;

    @Autowired
    private CampCacheService campCacheService;


    @Cacheable(value = Keys.CACHE_WORKER, key = "#id", condition = " #result != null ")
    public WorkerV getById(String id) {

        Worker worker = workerRepo.findById(id).orElse(null);

        return ObjectConvertTool.convert(worker, WorkerV.class);

    }

    @Transactional
    @CacheEvict(value = Keys.CACHE_WORKER, key = "#param.id")
    public void updateWorker(UpdateWorker param) {

        Worker worker = workerRepo.findById(param.getId()).orElseThrow(() -> BizException.notFound("人员不存在"));

        if (param.getPhotoId() != null) {
            BizAssert.notNull(
                    storageCacheService.getById(param.getPhotoId()),
                    BizException.notFound("人员图片不存在")
            );
        }

        if (param.getCampId() != null) {
            BizAssert.notNull(
                    campCacheService.getAllCamps().get(param.getCampId() + ""),
                    BizException.notFound("阵营信息不存在")
            );
        }

        BeanUtils.copyProperties(param, worker, ObjUtils.findFieldNameOfValueIsNull(param));

        workerRepo.save(worker);

    }

    @Transactional
    @CacheEvict(value = Keys.CACHE_WORKER, key = "#ids.ids")
    public int deleteByIds(DeleteByStringIds ids) {
        return workerRepo.deleteByIds(ids.getIds());
    }

}
