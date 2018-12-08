package com.hzsxy.bighomework.controller;

import com.hzsxy.bighomework.entity.Class_Info;
import com.hzsxy.bighomework.entity.Student;
import com.hzsxy.bighomework.entity.Teacher;
import com.hzsxy.bighomework.entity.User;
import com.hzsxy.bighomework.repository.Class_InfoRepository;
import com.hzsxy.bighomework.repository.StudentRepository;
import com.hzsxy.bighomework.repository.TeacherRepository;
import com.hzsxy.bighomework.repository.UserRepository;
import com.hzsxy.bighomework.util.GetTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by 张晨 on 2018/11/28.
 */
@Controller
public class LoginController {

    @RequestMapping("/")
    public String turnToIndex(HttpSession httpSession){
        if (httpSession.getAttribute("type")==null)return "index";
        return "main";
    }
    @Autowired
    Class_InfoRepository class_infoRepository;

    @RequestMapping("/toSign")
    public String toSign(Model model,HttpSession httpSession){
        List<Class_Info> class_infos =  (List<Class_Info>)class_infoRepository.findAll();
        model.addAttribute("class_infos",class_infos);
        return "sign";
    }


    @Autowired
    UserRepository userRepository;

    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestParam(value = "account") String account,
                        @RequestParam(value = "password") String password,
                        HttpSession httpSession) {
        User user=userRepository.findByAccountAndPassword(account,password);
        if(user!=null){
            String identity=user.getType();
            httpSession.setAttribute("type",identity);
            if(identity.equals("student")){
                httpSession.setAttribute("info",user.getStudent_id_fk());
            }
            else if(identity.equals("teacher")){
                httpSession.setAttribute("info",user.getTeacher_id_fk());
            }
            else
                httpSession.setAttribute("info","unknow");
            return "successful";
        }
        else return "账号密码错误";
    }
    @RequestMapping("/logout")
    @ResponseBody
    public void logout(HttpSession httpSession){
        httpSession.invalidate();
        return ;
    }

    @RequestMapping("/sign")
    @ResponseBody
    public String sign(@RequestParam(value = "account") String account,
                     @RequestParam(value = "password") String password,
                     @RequestParam(value = "name") String name,
                     @RequestParam(value = "type") String type,
                       @RequestParam(value = "")String class_id){
        User user = userRepository.findByAccount(account);

        try {
            if(user == null){//可以注册
                user = new User();
                user.setAccount(account);
                user.setPassword(password);
                user.setType(type);
            }else{
                return "账号已存在";
            }
            if(type.equals("teacher")){//保存老师表
                Teacher teacher = new Teacher();
                teacher.setName(name);
                teacher.setTeacher_id(GetTime.getGuid());
                user.setTeacher_id_fk(teacher);
            }else if(type.equals("student")){//保存学生表
                Student student = new Student();
                student.setStudent_id(GetTime.getGuid());
                student.setName(name);
                if(!class_id.equals("-1")){
                    Class_Info class_info = class_infoRepository.findOne(class_id);
                    student.setClass_id_fk(class_info);
                }
                user.setStudent_id_fk(student);
            }
            userRepository.save(user);
            return "successful";
        }catch (Exception e){
            e.printStackTrace();
            return "注册失败";
        }

    }

    @RequestMapping("/unsubscribe")
    @ResponseBody
    public String unsubscribe(@RequestParam(value = "account") String account,
                              @RequestParam(value = "password") String password ){
        try {
            User user=userRepository.findByAccountAndPassword(account,password);
            if(user != null){
                userRepository.delete(user);
                return "successful";
            }
            return "不存在此用户，失败";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "消户失败";

    }
    @RequestMapping("/toUnsubscribe")
    public String toUnsubscribe(){
        return "unsubscribe";
    }
}
