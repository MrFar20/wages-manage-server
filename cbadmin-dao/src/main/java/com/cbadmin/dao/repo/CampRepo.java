package com.cbadmin.dao.repo;

import com.cbadmin.dao.entity.Camp;
import com.cbmai.dao.BaseRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CampRepo extends BaseRepo<Camp, Long> {

    @Modifying
    @Query(nativeQuery = true, value = "delete from camp where id in :ids")
    int deleteByIds(@Param("ids") Collection<Long> ids);

}
