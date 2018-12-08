package com.hzsxy.bighomework.repository;

import com.hzsxy.bighomework.entity.Student;
import com.hzsxy.bighomework.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 张晨 on 2018/11/28.
 */
@Repository
public interface UserRepository extends CrudRepository<User,String>{

    User findByAccountAndPassword(String account,String password);

    User findByAccount(String account);
}
