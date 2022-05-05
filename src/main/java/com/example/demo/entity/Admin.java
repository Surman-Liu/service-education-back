package com.example.demo.entity;

public class Admin {
    private Integer id;
    private String username;
    private String phone;
    private String password;
    private String touxiang;
    private Integer job;

    public Admin(){}

    public Admin(Integer id, String username, String phone, String password, String touxiang, Integer job) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.touxiang = touxiang;
        this.job = job;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }

    public Integer getJob() {
        return job;
    }

    public void setJob(Integer job) {
        this.job = job;
    }
}
