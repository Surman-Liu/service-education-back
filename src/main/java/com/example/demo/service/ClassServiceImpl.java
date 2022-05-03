package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.ClassDao;
import com.example.demo.entity.Class;
import com.example.demo.entity.ClassNum;
import com.example.demo.entity.PageResult;
import com.example.demo.entity.TypeNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public PageResult search(JSONObject jsonObject) {
        Integer teacher_id = jsonObject.getInteger("teacher_id");
        Integer class_type = jsonObject.getInteger("class_type");
        Integer page = jsonObject.getInteger("page");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Integer pageNum = (page - 1) * pageSize;
        List<Class> classList =  classDao.search(teacher_id,class_type,pageNum,pageSize);
        Integer total = classDao.total(teacher_id,class_type);

        PageResult pageResult = new PageResult(pageNum,pageSize,total,classList);
        return pageResult;
    }

    @Override
    public void delete(Integer id) {
        classDao.delete(id);
    }

    @Override
    public Class selectById(Integer id) {
        Class classObj = classDao.selectById(id);
        return classObj;
    }

    @Override
    public void update(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Integer type = jsonObject.getInteger("type");
        Integer money = jsonObject.getInteger("money");
        String desc = jsonObject.getString("desc");
        String poster = jsonObject.getString("poster");
        Integer status = jsonObject.getInteger("status");
        Integer id = jsonObject.getInteger("id");

        classDao.update(id,name,type,money,desc,poster,status);
    }

    @Override
    public ClassNum classNum(Integer teacher_id,Integer class_type) {
        Integer publish = classDao.classNum(teacher_id,class_type,1);
        Integer audit = classDao.classNum(teacher_id,class_type,2);
        Integer refuse = classDao.classNum(teacher_id,class_type,3);
        ClassNum classNum = new ClassNum(publish,audit,refuse);
        return classNum;
    }

    @Override
    public TypeNum typeCount(Integer teacher_id) {
        Integer classes = classDao.typeCount(teacher_id,1);
        Integer exam = classDao.typeCount(teacher_id,2);
        Integer trail = classDao.typeCount(teacher_id,3);

        TypeNum typeNum = new TypeNum(classes,exam,trail);
        return typeNum;
    }
}
