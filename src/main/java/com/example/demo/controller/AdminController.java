package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Admin;
import com.example.demo.entity.PageResult;
import com.example.demo.entity.Student;
import com.example.demo.global.Response;
import com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /*
     * 用户密码登录
     * */
    @ResponseBody
    @RequestMapping("/login")
    public Response login(String phone, String password, HttpSession httpSession){
        try{
            Admin admin = adminService.login(phone,password);
            httpSession.setAttribute("user",admin);
            return Response.success(admin,"登陆成功");
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
            Admin admin = adminService.messageLogin(phone,code,httpSession);
            httpSession.setAttribute("user",admin);
            return Response.success(admin,"登陆成功");
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
            adminService.forgetPassword(phone,code,newPassword,httpSession);
            return Response.success("重置密码成功");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }

    /*
     * 获取所有管理员数据
     * */
    @ResponseBody
    @RequestMapping("/admin-all")
    public Response adminAll(Integer page,Integer pageSize){
        try{
            PageResult pageResult = adminService.adminAll(page,pageSize);
            return Response.success(pageResult,"成功");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }

    /*
     * 关键字搜索管理员
     * */
    @ResponseBody
    @RequestMapping("/search")
    public Response searchAdmin(@RequestBody JSONObject jsonObject){
        try{
            PageResult pageResult = adminService.search(jsonObject);
            return Response.success(pageResult,"成功");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }
}
