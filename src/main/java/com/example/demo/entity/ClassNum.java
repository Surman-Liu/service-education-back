package com.example.demo.entity;

public class ClassNum {
    private Integer publish;
    private Integer audit;
    private Integer refuse;

    public ClassNum(){}

    public ClassNum(Integer publish, Integer audit, Integer refuse) {
        this.publish = publish;
        this.audit = audit;
        this.refuse = refuse;
    }

    public Integer getPublish() {
        return publish;
    }

    public void setPublish(Integer publish) {
        this.publish = publish;
    }

    public Integer getAudit() {
        return audit;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    public Integer getRefuse() {
        return refuse;
    }

    public void setRefuse(Integer refuse) {
        this.refuse = refuse;
    }
}
