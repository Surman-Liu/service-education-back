package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.ClassDao;
import com.example.demo.entity.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClassServiceImpl implements ClassService{
    ClassDao classDao;
    @Autowired
    public ClassServiceImpl(ClassDao classDao) {
        this.classDao = classDao;
    }

    @Override
    public void add(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Integer type = jsonObject.getInteger("type");
        Integer money = jsonObject.getInteger("money");
        String desc = jsonObject.getString("desc");
        String poster = jsonObject.getString("poster");
        Integer status = jsonObject.getInteger("status");
        Integer teacher_id = jsonObject.getInteger("teacher_id");
        String teacher_name = jsonObject.getString("teacher_name");
        String create_time = jsonObject.getString("create_time");

        Class classObj = new Class(name,teacher_id,teacher_name,create_time,desc,money,poster,type,status);
        classDao.add(classObj);
    }
}
