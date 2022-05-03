package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.*;
import com.example.demo.entity.Class;
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

    /*
    * 查询对应的课程
    * */
    @ResponseBody
    @RequestMapping("/searchClass")
    public Response search(@RequestBody JSONObject jsonObject){
        try{
            PageResult pageResult = classService.search(jsonObject);
            return Response.success(pageResult,"加载完毕");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }

    /*
     * 删除课程
     * */
    @ResponseBody
    @RequestMapping("/delete")
    public Response delete(Integer id){
        try{
            classService.delete(id);
            return Response.success("删除成功");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }

    /*
     * 根据id选择课程
     * */
    @ResponseBody
    @RequestMapping("/selectById")
    public Response selectById(Integer id){
        try{
            Class classObj = classService.selectById(id);
            return Response.success(classObj,"成功");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }

    /*
     * 根据id更新课程
     * */
    @ResponseBody
    @RequestMapping("/update")
    public Response update(@RequestBody JSONObject jsonObject){
        try{
            classService.update(jsonObject);
            return Response.success("编辑成功");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }

    /*
     * 查询不同状态课程数量
     * */
    @ResponseBody
    @RequestMapping("/classNum")
    public Response classNum(Integer teacher_id,Integer class_type){
        try{
            ClassNum classNum = classService.classNum(teacher_id,class_type);
            return Response.success(classNum,"成功");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }

    /*
    * 查询某个老师不同类型的课程数量
    * */
    @ResponseBody
    @RequestMapping("/typeCount")
    public Response typeCount(Integer teacher_id){
        try{
            TypeNum typeNum = classService.typeCount(teacher_id);
            return Response.success(typeNum,"成功");
        }catch (RuntimeException e){
            return Response.error(e.getMessage());
        }
    }
}
