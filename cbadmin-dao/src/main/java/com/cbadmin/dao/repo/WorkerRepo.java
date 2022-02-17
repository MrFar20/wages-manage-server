package com.cbadmin.dao.repo;

import com.cbadmin.dao.entity.Worker;
import com.cbmai.dao.BaseRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface WorkerRepo extends BaseRepo<Worker, String> {

    @Query(nativeQuery = true, value = "delete from worker where id in :ids")
    @Modifying
    int deleteByIds(@Param("ids") Collection<String> ids);

}
