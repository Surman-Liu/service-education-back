package com.example.demo.dao;

import com.example.demo.entity.Teacher;
import com.example.demo.entity.User;

public interface TeacherDao {
    //    通过手机号查找用户
    Teacher findByUserPhone(String phone);

    //    用户注册
    void save(Teacher teacher);

    //    用户修改密码
    void updatePassword(String phone, String newPassword);

    // 用户修改基本信息
    void editInfo(String touxiang, String username, String words, String introduce, String phone);
}
