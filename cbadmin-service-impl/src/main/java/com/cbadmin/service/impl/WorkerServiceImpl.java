package com.cbadmin.service.impl;

import com.cbadmin.dao.entity.Worker;
import com.cbadmin.dao.repo.WorkerRepo;
import com.cbadmin.model.param.worker.AddWorker;
import com.cbadmin.model.param.worker.UpdateWorker;
import com.cbadmin.model.vo.WorkerV;
import com.cbadmin.service.WorkerService;
import com.cbadmin.service.impl.cache.CampCacheService;
import com.cbadmin.service.impl.cache.StorageCacheService;
import com.cbadmin.service.impl.cache.WorkerCacheService;
import com.cbmai.common.exception.BizException;
import com.cbmai.common.query.QueryCondition;
import com.cbmai.common.result.R;
import com.cbmai.common.util.BizAssert;
import com.cbmai.core.converter.ObjectConvertTool;
import com.cbmai.core.id.IdGenerator;
import com.cbmai.core.param.DeleteByStringIds;
import com.cbmai.service.util.JpaUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class WorkerServiceImpl implements WorkerService {

    @Autowired
    private WorkerRepo workerRepo;

    @Autowired
    private StorageCacheService storageCacheService;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private WorkerCacheService workerCacheService;

    @Autowired
    private CampCacheService campCacheService;

    @Override
    @Transactional
    public List<WorkerV> getByIds(Collection<String> ids) {
        List<Worker> allById = workerRepo.findAllById(ids);
        return ObjectConvertTool.convert(allById, WorkerV.class);
    }

    @Override
    public WorkerV getById(String id) {
        return workerCacheService.getById(id);
    }

    @Override
    @Transactional
    public void addWorker(AddWorker param) {

        BizAssert.notNull(
                storageCacheService.getById(param.getPhotoId()),
                BizException.notFound("人员图片不存在")
        );

        if (param.getCampId() != null) {
            BizAssert.notNull(
                    campCacheService.getAllCamps().get(param.getCampId() + ""),
                    BizException.notFound("阵营信息不存在")
            );
        }

        String id = idGenerator.generateUUID();
        Worker worker = new Worker();
        worker.setId(id);

        BeanUtils.copyProperties(param, worker);

        workerRepo.save(worker);
    }

    @Override
    public void updateWorker(UpdateWorker updateWorker) {
        workerCacheService.updateWorker(updateWorker);
    }

    @Override
    public int deleteByIds(DeleteByStringIds ids) {
        return workerCacheService.deleteByIds(ids);
    }

    @Override
    public R<?> query(QueryCondition queryCondition) {

        R r = JpaUtil.query(queryCondition, workerRepo);

        r.setData(
                ObjectConvertTool.convert((List<Worker>) r.getData(), WorkerV.class)
        );

        return r;
    }
}
