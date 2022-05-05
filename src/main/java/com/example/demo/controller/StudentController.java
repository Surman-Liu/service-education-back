package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.PageResult;
import com.example.demo.entity.Student;
import com.example.demo.entity.TypeNum;
import com.example.demo.entity.User;
import com.example.demo.global.Response;
import com.example.demo.service.StudentService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/student")
public class StudentController {
    private StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /*
     * 用户密码登录
     * */
    @ResponseBody
    @RequestMapping("/login")
    public Response login(String phone, String password, HttpSession httpSession){
        try{
            Student student = studentService.login(phone,password);
            httpSession.setAttribute("user",student);
            return Response.success(student,"登陆成功");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }

    /*
     * 用户验证码登录
     * */
    @ResponseBody
    @RequestMapping("/messageLogin")
    public Response messageLogin(String phone, String code, HttpSession httpSession){
        try{
            Student student = studentService.messageLogin(phone,code,httpSession);
            httpSession.setAttribute("user",student);
            return Response.success(student,"登陆成功");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }

    /*
     * 用户修改密码
     * */
    @ResponseBody
    @RequestMapping("/forgetPassword")
    public Response messageLogin(String phone, String code,String newPassword, HttpSession httpSession){
        try{
            studentService.forgetPassword(phone,code,newPassword,httpSession);
            return Response.success("重置密码成功");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }

    /*
     * 用户修改基本信息
     * */
    @ResponseBody
    @RequestMapping("/editInfo")
    public Response editInfo(@RequestBody JSONObject jsonObject, HttpSession httpSession){
        try{
            Student student = studentService.editInfo(jsonObject);
            httpSession.setAttribute("user",student);
            return Response.success(student,"修改成功");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }

    /*
     * 用户注册
     * @return
     * */
    @ResponseBody
    @RequestMapping("/register")
    public Response register(@RequestBody JSONObject jsonObject, HttpSession httpSession){
        try {
            studentService.register(jsonObject,httpSession);
            return Response.success("注册成功，欢迎登陆！");
        }catch(RuntimeException e){
            return Response.error(e.getMessage());
        }
    }

    /*
    * 学生选择的某个类型的课程
    * */
    @ResponseBody
    @RequestMapping("/selectedClass")
    public Response selectedClass(@RequestBody JSONObject jsonObject){
        try {
            PageResult pageResult = studentService.selectedClass(jsonObject);
            return Response.success(pageResult,"成功");
        }catch(RuntimeException e){
            return Response.error(e.getMessage());
        }
    }

    /*
     * 学生选择的不同类型的课程数量
     * */
    @ResponseBody
    @RequestMapping("/typeCount")
    public Response typeCount(Integer student_id){
        try{
            TypeNum typeNum = studentService.typeCount(student_id);
            return Response.success(typeNum,"成功");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }

    /*
     * 获取所有学生数据
     * */
    @ResponseBody
    @RequestMapping("/student-all")
    public Response studentAll(Integer page,Integer pageSize){
        try{
            PageResult pageResult = studentService.studentAll(page,pageSize);
            return Response.success(pageResult,"成功");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }

    /*
     * 关键字搜索学生
     * */
    @ResponseBody
    @RequestMapping("/search")
    public Response searchStudent(@RequestBody JSONObject jsonObject){
        try{
            PageResult pageResult = studentService.search(jsonObject);
            return Response.success(pageResult,"成功");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }

    /*
     * 删除学生
     * */
    @ResponseBody
    @RequestMapping("/delete")
    public Response delete(Integer id){
        try{
            studentService.delete(id);
            return Response.success("删除成功");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }
}
