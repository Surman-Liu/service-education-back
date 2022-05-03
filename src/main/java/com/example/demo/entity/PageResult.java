package com.example.demo.entity;

import java.util.List;

public class PageResult {
    private Integer pageNum;
    private Integer pageSize;
    private Integer total;
    private List content;

    public PageResult(){};

    public PageResult(Integer pageNum, Integer pageSize, Integer total, List content) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.content = content;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List getContent() {
        return content;
    }

    public void setContent(List content) {
        this.content = content;
    }
}
