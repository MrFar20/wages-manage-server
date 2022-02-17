package com.cbadmin.service;

import com.cbadmin.model.param.worker.AddWorker;
import com.cbadmin.model.param.worker.UpdateWorker;
import com.cbadmin.model.vo.WorkerV;
import com.cbmai.common.service.DataQueryService;
import com.cbmai.core.param.DeleteByStringIds;

import java.util.Collection;
import java.util.List;

public interface WorkerService extends DataQueryService {


    /**
     * 通过ids查找
     * @param ids
     * @return
     */
    List<WorkerV> getByIds(Collection<String> ids);

    /**
     * 通过id获取
     * @param id
     * @return
     */
    WorkerV getById(String id);

    /**
     * 获取worker
     * @param param
     */
    void addWorker(AddWorker param);


    /**
     * 更新worker
     * @param updateWorker
     */
    void updateWorker(UpdateWorker updateWorker);


    /**
     * 删除
     * @param ids
     */
    int deleteByIds(DeleteByStringIds ids);

}
