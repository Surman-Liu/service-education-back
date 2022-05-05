package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Admin;
import com.example.demo.entity.PageResult;

import javax.servlet.http.HttpSession;

public interface AdminService {
    Admin login(String phone, String password);

    Admin messageLogin(String phone, String code, HttpSession httpSession);

    void forgetPassword(String phone, String code, String newPassword, HttpSession httpSession);

    PageResult adminAll(Integer page, Integer pageSize);

    PageResult search(JSONObject jsonObject);
}
