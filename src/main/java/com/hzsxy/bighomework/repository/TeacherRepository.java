package com.hzsxy.bighomework.repository;

import com.hzsxy.bighomework.entity.Teacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 张晨 on 2018/11/28.
 */
@Repository
public interface TeacherRepository extends CrudRepository<Teacher,String> {

}
