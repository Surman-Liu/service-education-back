package com.example.demo.dao;

import com.example.demo.entity.User;

public interface UserDao {
//    通过手机号查找用户
    User findByUserPhone(String phone);

//    用户注册
    void save(User user);

//    用户修改密码
    void updatePassword(String phone, String newPassword);
}
