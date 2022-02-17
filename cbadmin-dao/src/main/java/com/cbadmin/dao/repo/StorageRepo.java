package com.cbadmin.dao.repo;

import com.cbadmin.dao.entity.Storage;
import com.cbmai.dao.BaseRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * 存储repo
 */
public interface StorageRepo extends BaseRepo<Storage, String> {


    /**
     * 通过id删除
     * @param storageId
     * @return
     */
    @Query(nativeQuery = true, value = "delete from storage where id=:storageId")
    @Modifying
    int _deleteById(@Param("storageId") String storageId);


    /**
     *
     * @param ids
     * @return
     */
    List<Storage> findByIdIn(Collection<String> ids);

}
