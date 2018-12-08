package com.hzsxy.bighomework.repository;

import com.hzsxy.bighomework.entity.List_Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 张晨 on 2018/11/28.
 */
@Repository
public interface List_QuestionRepository extends CrudRepository<List_Question,Integer> {

}
