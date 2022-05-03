package com.example.demo.dao;

import com.example.demo.entity.Class;
import com.example.demo.entity.ClassNum;

import java.util.List;

public interface ClassDao {
    void add(Class classObj);

    List<Class> search(Integer teacher_id, Integer class_type, Integer pageNum, Integer pageSize);

    Integer total(Integer teacher_id,Integer class_type);

    void delete(Integer id);

    Class selectById(Integer id);

    void update(Integer id,String name, Integer type, Integer money, String desc, String poster, Integer status);

    Integer classNum(Integer teacher_id, Integer class_type, Integer status);

    Integer typeCount(Integer teacher_id,Integer class_type);

}
