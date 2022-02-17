package com.cbadmin.dao.repo;

import com.cbadmin.dao.entity.User;
import com.cbmai.dao.BaseRepo;
import org.springframework.stereotype.Repository;

/**
 * 用户 repo
 */
@Repository
public interface UserRepo extends BaseRepo<User, String> {

    /**
     * 通过用户名查找
     * @param username 用户名
     * @return
     */
    User findByUsername(String username);

}
