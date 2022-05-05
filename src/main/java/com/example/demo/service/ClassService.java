package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Class;
import com.example.demo.entity.ClassNum;
import com.example.demo.entity.PageResult;
import com.example.demo.entity.TypeNum;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ClassService {
    /*
    * 新增课程
    * */
    void add(JSONObject jsonObject);

    /*
    * 查询课程
    * */
    PageResult search(JSONObject jsonObject);

    /*
    * 删除课程
    * */
    void delete(Integer id);

    /*
    * 根据id选择课程
    * */
    Class selectById(Integer id);

    /*
    * 更新课程
    * */
    void update(JSONObject jsonObject);

    /*
    * 查询不同类型和状态课程数量
    * */
    ClassNum classNum(Integer teacher_id,Integer class_type);

    /*
    * 查看不同类型的课程数量
    * */
    TypeNum typeCount(Integer teacher_id);

    PageResult classManage(JSONObject jsonObject);

    void changeStatus(Integer id, Integer status);

    PageResult classAudited(JSONObject jsonObject);
}
