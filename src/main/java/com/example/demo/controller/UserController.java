package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.User;
import com.example.demo.global.Response;
import com.example.demo.service.UserService;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
    * 用户密码登录
    * */
    @ResponseBody
    @RequestMapping("/login")
    public Response login(String phone, String password, Integer job, HttpSession httpSession){
        try{
            User user = userService.login(phone,password,job);
            httpSession.setAttribute("user",user);
            return Response.success(user,"登陆成功");
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
            User user = userService.messageLogin(phone,code,httpSession);
            httpSession.setAttribute("user",user);
            return Response.success(user,"登陆成功");
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
            userService.forgetPassword(phone,code,newPassword,httpSession);
            return Response.success("重置密码成功");
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
    public Response register(@RequestBody JSONObject jsonObject,HttpSession httpSession){
        try {
            userService.register(jsonObject,httpSession);
            return Response.success("注册成功，欢迎登陆！");
        }catch(RuntimeException e){
            return Response.error(e.getMessage());
        }
    }
}
