package com.example.demo.dao;

import com.example.demo.entity.Admin;

import java.util.List;

public interface AdminDao {
    Admin findByUserPhone(String phone);

    void updatePassword(String phone, String passwordSecret);

    List<Admin> adminAll(Integer pageNum, Integer pageSize);

    Integer adminAllCount();

    List<Admin> search(String input, Integer pageNum, Integer pageSize);

    Integer searchCount(String input);

    void delete(Integer id);

    void add(String username, String phone, String passwordSecret);
}
