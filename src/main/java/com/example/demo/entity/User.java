package com.example.demo.entity;

public class User {
    private Integer id;
    private String username;
    private String realname;
    private String password;
    private Integer job;
    private String phone;

    public User(){

    }

    public User(String username, String realname, String password, Integer job, String phone) {
        this.username = username;
        this.realname = realname;
        this.password = password;
        this.job = job;
        this.phone = phone;
    }

    public User(Integer id, String username, String realname, String password, Integer job, String phone) {
        this.id = id;
        this.username = username;
        this.realname = realname;
        this.password = password;
        this.job = job;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getJob() {
        return job;
    }

    public void setJob(Integer job) {
        this.job = job;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
