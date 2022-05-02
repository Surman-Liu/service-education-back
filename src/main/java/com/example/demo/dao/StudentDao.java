package com.example.demo.dao;

import com.example.demo.entity.Student;

public interface StudentDao {
    //    通过手机号查找用户
    Student findByUserPhone(String phone);

    //    用户注册
    void save(Student student);

    //    用户修改密码
    void updatePassword(String phone, String newPassword);

    // 用户修改个人信息
    void editInfo(String touxiang, String username, String words, String introduce, String phone);
}
