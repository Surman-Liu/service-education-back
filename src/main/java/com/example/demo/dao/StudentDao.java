package com.example.demo.dao;

import com.example.demo.entity.Class;
import com.example.demo.entity.Student;

import java.util.List;

public interface StudentDao {
    //    通过手机号查找用户
    Student findByUserPhone(String phone);

    //    用户注册
    void save(Student student);

    //    用户修改密码
    void updatePassword(String phone, String newPassword);

    // 用户修改个人信息
    void editInfo(String touxiang, String username, String words, String introduce, String phone);

    //学生选择的某一类课程
    List<Class> selectedClass(Integer student_id, Integer class_type, Integer pageNum, Integer pageSize);

    //学生选择的某一类课程的总数
    Integer total(Integer student_id, Integer class_type);

    //学生选择的不同类型的课程数量
    Integer typeCount(Integer student_id, Integer class_type);

    List<Student> studentAll(Integer pageNum, Integer pageSize);

    Integer studentAllCount();

    List<Student> search(String input, Integer pageNum, Integer pageSize);

    Integer searchCount(String input);
}
