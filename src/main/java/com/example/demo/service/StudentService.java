package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Student;
import com.example.demo.entity.User;

import javax.servlet.http.HttpSession;

public interface StudentService {
    /*
     * 用户注册
     * */
    void register(JSONObject jsonObject, HttpSession session);

    /*
     * 用户使用密码登录
     * */
    Student login(String phone, String password);

    /*
     * 用户使用验证码登录
     * */
    Student messageLogin(String phone,String code,HttpSession httpSession);

    /*
     * 用户忘记密码
     * */
    void forgetPassword(String phone, String code, String newPassword,HttpSession httpSession);

    /*
    * 用户编辑个人信息
    * */
    Student editInfo(JSONObject jsonObject);
}