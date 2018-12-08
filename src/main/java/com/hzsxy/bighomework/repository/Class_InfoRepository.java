package com.hzsxy.bighomework.repository;


import com.hzsxy.bighomework.entity.Class_Info;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 张晨 on 2018/11/28.
 */
@Repository
public interface Class_InfoRepository extends CrudRepository<Class_Info,String> {
    @Query("select class_info from Class_Info class_info where class_info.teacher_id_fk.teacher_id=?1")
    Iterable<Class_Info> findAllByTeacher_id(String teacher);
}
