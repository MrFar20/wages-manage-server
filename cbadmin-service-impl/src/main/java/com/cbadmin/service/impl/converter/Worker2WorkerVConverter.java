package com.cbadmin.service.impl.converter;

import com.cbadmin.dao.entity.Worker;
import com.cbadmin.model.vo.CampV;
import com.cbadmin.model.vo.StorageV;
import com.cbadmin.model.vo.WorkerV;
import com.cbadmin.service.CampService;
import com.cbadmin.service.impl.cache.StorageCacheService;
import com.cbadmin.service.impl.util.StorageUtils;
import com.cbmai.core.converter.BaseObjectConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Worker2WorkerVConverter extends BaseObjectConverter<Worker, WorkerV> {
    @Autowired
    private StorageCacheService storageCacheService;

    @Autowired
    private CampService campService;

    private static ThreadLocal<Map<String, CampV>> campMap = new ThreadLocal<>();

    @Override
    public WorkerV fromEntity(Worker entity) {
        WorkerV workerV =  super.fromEntity(entity);

        StorageV storage = storageCacheService.getById(entity.getPhotoId());

        if (storage != null) {
            workerV.setPhotoUrl(StorageUtils.setAccessUrl(storage));
        }

        if (entity.getCampId() != null) {
            if (campMap.get() == null) {
                Map<String, CampV> allCamps = campService.getAllCamps();
                campMap.set(allCamps);
            }
            CampV value = campMap.get().get(entity.getCampId() + "");
            if (value != null) {
                workerV.setCampName(value.getName());
                workerV.setCampColor(value.getColor());
            }
        }

        return workerV;
    }


    @Override
    public List<WorkerV> fromEntitys(List<Worker> entitys) {
        List<WorkerV> list = super.fromEntitys(entitys);
        campMap.remove();
        StorageUtils.clear();
        return list;
    }
}
