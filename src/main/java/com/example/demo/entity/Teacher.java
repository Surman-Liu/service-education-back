package com.example.demo.entity;

public class Teacher {
    private Integer id;
    private String realname;
    private String username;
    private String phone;
    private String idcard;
    private Integer status;
    private String classes;
    private String exp;
    private String exam;
    private String touxiang;
    private String introduce;
    private String words;
    private String password;
    private Integer job;

    public Teacher(){

    }

    public Teacher(String username, String realname, String password, String phone,String idcard) {
        this.username = username;
        this.realname = realname;
        this.password = password;
        this.phone = phone;
        this.idcard = idcard;
    }

    public Teacher(Integer id, String realname, String username, String phone, String idcard, Integer status, String classes, String exp, String exam, String touxiang, String introduce, String words, String password, Integer job) {
        this.id = id;
        this.realname = realname;
        this.username = username;
        this.phone = phone;
        this.idcard = idcard;
        this.status = status;
        this.classes = classes;
        this.exp = exp;
        this.exam = exam;
        this.touxiang = touxiang;
        this.introduce = introduce;
        this.words = words;
        this.password = password;
        this.job = job;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
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

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
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
}
