package com.example.demo.service;

import javax.servlet.http.HttpSession;

public interface UtilsService {
    Boolean sendCode(String phone, HttpSession httpSession) throws Exception;
}
