package com.cbadmin.service.impl;

import com.cbadmin.dao.entity.Camp;
import com.cbadmin.dao.repo.CampRepo;
import com.cbadmin.model.LabelValue;
import com.cbadmin.model.param.camp.AddCamp;
import com.cbadmin.model.param.camp.UpdateCamp;
import com.cbadmin.model.vo.CampV;
import com.cbadmin.service.CampService;
import com.cbadmin.service.impl.cache.CampCacheService;
import com.cbmai.common.query.QueryCondition;
import com.cbmai.common.result.R;
import com.cbmai.core.converter.ObjectConvertTool;
import com.cbmai.core.param.DeleteByLongIds;
import com.cbmai.service.util.JpaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CampServiceImpl implements CampService {

    @Autowired
    private CampCacheService campCacheService;

    @Autowired
    private CampRepo campRepo;

    @Override
    public Map<String, CampV> getAllCamps() {
        return campCacheService.getAllCamps();
    }

    @Override
    public Map<String, LabelValue<Long>> getAllCampsLabelValue() {
        Map<String, LabelValue<Long>> map  = new HashMap<>();
        campCacheService.getAllCamps().forEach((k, v) -> map.put(k + "", new LabelValue<>(v.getName(), v.getId())));
        return map;
    }

    @Override
    public void addCamp(AddCamp param) {
        campCacheService.addCamp(param);
    }

    @Override
    public void updateCamp(UpdateCamp param) {
        campCacheService.updateCamp(param);
    }

    @Override
    public int deleteCamps(DeleteByLongIds ids) {
        return campCacheService.deleteCamps(ids);
    }

    @Override
    public R<?> query(QueryCondition queryCondition) {
        R r = JpaUtil.query(queryCondition, campRepo);
        r.setData(
                ObjectConvertTool.convert(
                        (List<Camp>) r.getData(), CampV.class
                )
        );
        return r;
    }
}
