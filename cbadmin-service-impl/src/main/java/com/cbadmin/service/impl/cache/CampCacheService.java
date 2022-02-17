package com.cbadmin.service.impl.cache;

import com.cbadmin.common.constant.Keys;
import com.cbadmin.dao.entity.Camp;
import com.cbadmin.dao.repo.CampRepo;
import com.cbadmin.model.param.camp.AddCamp;
import com.cbadmin.model.param.camp.UpdateCamp;
import com.cbadmin.model.vo.CampV;
import com.cbmai.common.exception.BizException;
import com.cbmai.common.util.StringUtils;
import com.cbmai.core.converter.ObjectConvertTool;
import com.cbmai.core.param.DeleteByLongIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CampCacheService  {

    @Autowired
    private CampRepo campRepo;


    @Cacheable(value = Keys.CACHE_CAMP_ALL, key = "'all'")
    public Map<String, CampV> getAllCamps() {

        List<Camp> camps = campRepo.findAll();

        Map<String, CampV> values = new HashMap<>();

        camps.forEach(camp -> {
            values.put(camp.getId() + "", ObjectConvertTool.convert(camp, CampV.class));
        });

        return values;
    }

    @Transactional
    @CacheEvict(value = Keys.CACHE_CAMP_ALL, key = "'all'")
    public void addCamp(AddCamp param) {

        Camp camp = new Camp();
        camp.setName(param.getName());
        camp.setColor(param.getColor());

        campRepo.save(camp);

    }

    @Transactional
    @CacheEvict(value = Keys.CACHE_CAMP_ALL, key = "'all'")
    public void updateCamp(UpdateCamp param) {

        Camp camp = campRepo.findById(param.getId()).orElseThrow(() -> BizException.notFound("阵营不存在"));
        if (!StringUtils.isNullOrBlank(param.getName())) {
            camp.setName(param.getName());
        }
        if (param.getColor() != null) {
            camp.setColor(param.getColor());
        }

        campRepo.save(camp);

    }

    @Transactional
    @CacheEvict(value = Keys.CACHE_CAMP_ALL, key = "'all'")
    public int deleteCamps(DeleteByLongIds param) {
        return campRepo.deleteByIds(param.getIds());
    }

}
