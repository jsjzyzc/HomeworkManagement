package com.hzsxy.bighomework.controller;

import com.hzsxy.bighomework.entity.*;
import com.hzsxy.bighomework.model.Homework_View;
import com.hzsxy.bighomework.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by 张晨 on 2018/11/28
 */
@Controller
public class StudentController implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession httpSession = httpServletRequest.getSession();
        if (httpSession.getAttribute("type") == "student") return true;
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    @Autowired
    HomeworkRepository homeworkRepository;
    @Autowired
    List_Question_SetRepository list_question_setRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    Student_SubmitRepository student_submitRepository;
    @Autowired
    StudentRepository studentRepository;

    @RequestMapping("/homework")
    public String returnHomework(Model model,HttpSession httpSession){
        Student student= (Student) httpSession.getAttribute("info");
        String class_info_id=student.getClass_id_fk().getClass_id();
        List<Homework> homeworks = (List<Homework>) homeworkRepository.findAllByClass_id(class_info_id);
        List<Homework_View> homework_views=new ArrayList<Homework_View>();
        for(Homework homework:homeworks){
            Homework_View homework_view = new Homework_View();
            homework_view.setHomework_pk(homework.getHomework_pk());
            List<List_Question_Set> list_question_sets = (List<List_Question_Set>) list_question_setRepository.findAllByList_question_id(homework.getHomework_pk().getList_question_id_fk().getList_question_id());
            homework_view.setList_question_sets(list_question_sets);
            homework_views.add(homework_view);
        }
        model.addAttribute("homework_info",homework_views);
        return "student/homework";
    }

    @RequestMapping("/answerpage")
    public String returnAnswerpage(@RequestParam("question_id") int question_id,Model model, HttpSession httpSession){
        Question question=questionRepository.findOne(question_id);
        model.addAttribute("question",question);
        Student info = (Student)httpSession.getAttribute("info");
        Student_Submit submit =  student_submitRepository.findByStudentIdAndQuestionId(info.getStudent_id(),question_id);
        if(submit == null){
            submit = new Student_Submit();
        }
        model.addAttribute("submit",submit);
        return "student/submit-answer";
    }

    @RequestMapping(value = "/submit-answer",method = {RequestMethod.PUT})
    @ResponseBody
    public String submitAnswer(HttpServletRequest httpServletRequest,HttpSession httpSession){
        int question_id= Integer.parseInt(httpServletRequest.getParameter("question_id"));
        String student_id = httpServletRequest.getParameter("student_id");
        try{
            String answer =httpServletRequest.getParameter("answer");
            if(StringUtils.isEmpty(student_id)){//新增
                Student student= (Student) httpSession.getAttribute("info");
                Question question=questionRepository.findOne(question_id);
                if(question!=null) {
                    Student_Submit student_submit = new Student_Submit(new Student_Submit_PK(student, question), answer, new Timestamp(new Date().getTime()));
                    student_submitRepository.save(student_submit);
                    return "seccussful";
                }
            }else{//修改
                Student_Submit submit = new Student_Submit();
                submit.setQuestion_id(question_id);
                submit.setStudent_id(student_id);
                submit.setAnswer(answer);
                submit.setSubmit_time(new Timestamp(new Date().getTime()));
                student_submitRepository.save(submit);
                return "seccussful";
            }
            return "failed";
        }catch (Exception e){
            return "failed";
        }
    }

    /**
     *
     * 此接口说明一下，只能修改学生姓名，如果修改了class和teacher
     * 会跟实际数据对应不上，造成脏数据的产生。再加上数据库有外键关联
     * 会造成很多不必要的异常
     *
     * 这里告诉述说一些经验，数据库尽量不设置外键关联，这样业务维护性差，影响业务的拓展，
     * 而且影响性能
     *所有业务应在代码和sql里面去体现，而不是交给数据库
     *
     * 有机会多使用mybatis 比jpa要轻，而且也好维护
     *
     * @param model
     * @param httpSession
     * @param student_id
     * @param student_name
     * @return
     */
    @RequestMapping(value = "/editPersonalinfo",method = {RequestMethod.POST})
    @ResponseBody
    public String editPersonalinfo(Model model, HttpSession httpSession,
                                   @RequestParam("student_id")String student_id,
                                   @RequestParam("student_name")String student_name){
        try {
            Student student = studentRepository.findOne(student_id);
            student.setName(student_name);
            studentRepository.save(student);
            //改变session里面的值
            httpSession.setAttribute("info",student);
            return "seccussful";
        }catch (Exception e){
            e.printStackTrace();
            return "failed";
        }
    }

}