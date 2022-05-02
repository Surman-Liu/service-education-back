package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Student;
import com.example.demo.global.Response;
import com.example.demo.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/class")
public class ClassController {
    private ClassService classService;
    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    /*
     * 新建课程
     * */
    @ResponseBody
    @RequestMapping("/add")
    public Response add(@RequestBody JSONObject jsonObject){
        try{
            classService.add(jsonObject);
            return Response.success("创建成功，请耐心等待管理员审核哦~");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }
}
