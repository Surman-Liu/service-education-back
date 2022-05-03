package com.example.demo.entity;

public class TypeNum {
    private Integer classes;
    private Integer trail;
    private Integer exam;

    public TypeNum(){}

    public TypeNum(Integer classes, Integer exam, Integer trail) {
        this.classes = classes;
        this.trail = trail;
        this.exam = exam;
    }

    public Integer getClasses() {
        return classes;
    }

    public void setClasses(Integer classes) {
        this.classes = classes;
    }

    public Integer getTrail() {
        return trail;
    }

    public void setTrail(Integer trail) {
        this.trail = trail;
    }

    public Integer getExam() {
        return exam;
    }

    public void setExam(Integer exam) {
        this.exam = exam;
    }
}
