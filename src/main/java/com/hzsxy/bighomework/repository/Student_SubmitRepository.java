package com.hzsxy.bighomework.repository;

import com.hzsxy.bighomework.entity.Student_Submit;
import com.hzsxy.bighomework.entity.Student_Submit_PK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by 张晨 on 2018/11/28.
 */
@Repository
public interface Student_SubmitRepository extends CrudRepository<Student_Submit,Student_Submit_PK> {
    @Query(value = "select submit from Student_Submit submit where submit.student_id=?1 and  submit.question_id = ?2")
    Student_Submit findByStudentIdAndQuestionId(String student_id, int question_id);
    @Query(value = "select submit from Student_Submit submit where submit.question_id=?1")
    List<Student_Submit> getByQuestionId(Integer question_id);
}
