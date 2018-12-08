package com.hzsxy.bighomework.repository;

import com.hzsxy.bighomework.entity.List_Question_Set;
import com.hzsxy.bighomework.entity.List_Question_Set_PK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 张晨 on 2018/11/28.
 */
@Repository
public interface List_Question_SetRepository extends CrudRepository<List_Question_Set,List_Question_Set_PK> {

    @Query("select list_question_set from List_Question_Set list_question_set where list_question_set.list_question_set_pk.list_question_id_fk.list_question_id=?1")
    Iterable<List_Question_Set> findAllByList_question_id(int list_question_id);

}
