package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.PageResult;
import com.example.demo.entity.Teacher;
import com.example.demo.entity.User;

import javax.servlet.http.HttpSession;

public interface TeacherService {
    /*
     * 用户注册
     * */
    void register(JSONObject jsonObject, HttpSession session);

    /*
     * 用户使用密码登录
     * */
    Teacher login(String phone, String password);

    /*
     * 用户使用验证码登录
     * */
    Teacher messageLogin(String phone,String code,HttpSession httpSession);

    /*
     * 用户忘记密码
     * */
    void forgetPassword(String phone, String code, String newPassword,HttpSession httpSession);

    Teacher editInfo(JSONObject jsonObject);

    PageResult teacherAll(Integer page, Integer pageSize);

    PageResult search(JSONObject jsonObject);
}
