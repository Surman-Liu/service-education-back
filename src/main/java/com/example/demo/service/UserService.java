package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.User;

import javax.servlet.http.HttpSession;

public interface UserService {
    /*
    * 用户注册
    * */
    void register(JSONObject jsonObject, HttpSession session);

    /*
    * 用户使用密码登录
    * */
    User login(String phone, String password, Integer job);

    /*
    * 用户使用验证码登录
    * */
    User messageLogin(String phone,String code,HttpSession httpSession);

    /*
    * 用户忘记密码
    * */
    void forgetPassword(String phone, String code, String newPassword,HttpSession httpSession);
}
