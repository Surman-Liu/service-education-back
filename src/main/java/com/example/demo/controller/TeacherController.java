package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.PageResult;
import com.example.demo.entity.Student;
import com.example.demo.entity.Teacher;
import com.example.demo.entity.User;
import com.example.demo.global.Response;
import com.example.demo.service.TeacherService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private TeacherService teacherService;
    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    /*
     * 用户密码登录
     * */
    @ResponseBody
    @RequestMapping("/login")
    public Response login(String phone, String password, HttpSession httpSession){
        try{
            Teacher teacher = teacherService.login(phone,password);
            httpSession.setAttribute("user",teacher);
            return Response.success(teacher,"登陆成功");
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
            Teacher teacher = teacherService.messageLogin(phone,code,httpSession);
            httpSession.setAttribute("user",teacher);
            return Response.success(teacher,"登陆成功");
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
            teacherService.forgetPassword(phone,code,newPassword,httpSession);
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
            Teacher teacher = teacherService.editInfo(jsonObject);
            httpSession.setAttribute("user",teacher);
            return Response.success(teacher,"修改成功");
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
            teacherService.register(jsonObject,httpSession);
            return Response.success("注册成功，欢迎登陆！");
        }catch(RuntimeException e){
            return Response.error(e.getMessage());
        }
    }

    /*
     * 获取所有老师数据
     * */
    @ResponseBody
    @RequestMapping("/teacher-all")
    public Response teacherAll(Integer page,Integer pageSize){
        try{
            PageResult pageResult = teacherService.teacherAll(page,pageSize);
            return Response.success(pageResult,"成功");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }

    /*
     * 关键字搜索老师
     * */
    @ResponseBody
    @RequestMapping("/search")
    public Response searchTeacher(@RequestBody JSONObject jsonObject){
        try{
            PageResult pageResult = teacherService.search(jsonObject);
            return Response.success(pageResult,"成功");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }

    /*
     * 删除老师
     * */
    @ResponseBody
    @RequestMapping("/delete")
    public Response delete(Integer id){
        try{
            teacherService.delete(id);
            return Response.success("删除成功");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }
}
