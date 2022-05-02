package com.example.demo.entity;

import java.util.Date;

public class Class {
    private Integer id;
    private String class_name;
    private Integer teacher_id;
    private String teacher_name;
    private String create_time;
    private String description;
    private Integer money;
    private String poster;
    private Integer class_type;
    private Integer status;

    public Class() {
    }

    public Class(String class_name, Integer teacher_id, String teacher_name, String create_time, String description, Integer money, String poster, Integer class_type, Integer status) {
        this.class_name = class_name;
        this.teacher_id = teacher_id;
        this.teacher_name = teacher_name;
        this.create_time = create_time;
        this.description = description;
        this.money = money;
        this.poster = poster;
        this.class_type = class_type;
        this.status = status;
    }

    public Class(Integer id, String class_name, Integer teacher_id, String teacher_name, String create_time, String description, Integer money, String poster, Integer class_type, Integer status) {
        this.id = id;
        this.class_name = class_name;
        this.teacher_id = teacher_id;
        this.teacher_name = teacher_name;
        this.create_time = create_time;
        this.description = description;
        this.money = money;
        this.poster = poster;
        this.class_type = class_type;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public Integer getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Integer teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Integer getClass_type() {
        return class_type;
    }

    public void setClass_type(Integer class_type) {
        this.class_type = class_type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
