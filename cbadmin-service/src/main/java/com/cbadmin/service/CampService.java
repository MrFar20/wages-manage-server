package com.cbadmin.service;

import com.cbadmin.model.LabelValue;
import com.cbadmin.model.param.camp.AddCamp;
import com.cbadmin.model.param.camp.UpdateCamp;
import com.cbadmin.model.vo.CampV;
import com.cbmai.common.service.DataQueryService;
import com.cbmai.core.param.DeleteByLongIds;

import java.util.Map;

public interface CampService extends DataQueryService {

    /**
     * 获取所有阵营
     * @return
     */
    Map<String, CampV> getAllCamps();

    /**
     * 获取所有阵营的label
     * @return
     */
    Map<String, LabelValue<Long>> getAllCampsLabelValue();

    /**
     * 添加阵营
     * @param param
     */
    void addCamp(AddCamp param);

    /**
     * 更新阵营
     * @param param
     */
    void updateCamp(UpdateCamp param);

    /**
     * 删除阵营
     * @param ids
     * @return
     */
    int deleteCamps(DeleteByLongIds ids);

}
