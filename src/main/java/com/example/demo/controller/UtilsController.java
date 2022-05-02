package com.example.demo.controller;

import com.example.demo.global.Response;
import com.example.demo.service.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/utils")
public class UtilsController {
    private UtilsService utilsService;
    @Autowired
    public UtilsController(UtilsService utilsService) {
        this.utilsService = utilsService;
    }

    /**
     * 手机验证码登录
     */
    @ResponseBody
    @RequestMapping("/sendSmsLogin")
    private Response sendSmsLogin(String phone, HttpSession httpSession) {
        try{
            Boolean success = utilsService.sendCode(phone,httpSession);
            if(success){
                return Response.success("发送成功");
            }else{
                return Response.error("发送失败，请重试");
            }
        }catch (Exception e){
            return Response.error("发送失败，请重试");
        }

    }
}
