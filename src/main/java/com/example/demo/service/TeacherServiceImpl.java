package com.example.demo.service;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.StudentDao;
import com.example.demo.dao.TeacherDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService{
    private TeacherDao teacherDao;
    @Autowired
    public TeacherServiceImpl(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    @Override
    public Teacher login(String phone, String password) {
        Teacher teacher = teacherDao.findByUserPhone(phone);
        if(ObjectUtils.isEmpty(teacher)){
            throw  new RuntimeException("该用户不存在");
        }
        String passwordSecret = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if(!teacher.getPassword().equals(passwordSecret)){
            throw new RuntimeException("密码输入错误");
        }
        return teacher;
    }

    @Override
    public Teacher messageLogin(String phone, String code, HttpSession httpSession) {
        // 查找用户是否存在
        Teacher teahcerDB = teacherDao.findByUserPhone(phone);
        if(ObjectUtils.isEmpty(teahcerDB)){
            throw new RuntimeException("该用户不存在");
        }

        // 比较验证码
        JSONObject userCode = (JSONObject) httpSession.getAttribute("code");
        if(ObjectUtils.isEmpty(userCode)){
            throw new RuntimeException("请获取验证码");
        }

        String codePhone = userCode.getString("phone");
        String codeSession = userCode.getString("code");
        Long time = userCode.getLong("createTime");
        Long diff = System.currentTimeMillis() - time;

        if(!phone.equals(codePhone)){
            throw new RuntimeException("请重新获取验证码");
        }
        // 判断验证码是否有效（5分钟）
        if(diff / 1000 /60 > 5){
            throw new RuntimeException("验证码失效，请重新获取");
        }
        // 判断验证码是否相等
        if(!codeSession.equals(code)){
            throw new RuntimeException("验证码不正确，请重新输入");
        }

        return teahcerDB;
    }

    @Override
    public void forgetPassword(String phone, String code, String newPassword, HttpSession httpSession) {
        this.messageLogin(phone,code,httpSession);
        String passwordSecret = DigestUtils.md5DigestAsHex(newPassword.getBytes(StandardCharsets.UTF_8));
        teacherDao.updatePassword(phone,passwordSecret);
    }

    @Override
    public Teacher editInfo(JSONObject jsonObject) {
        String touxiang = jsonObject.getString("touxiang");
        String username = jsonObject.getString("username");
        String words = jsonObject.getString("words");
        String introduce = jsonObject.getString("introduce");
        String phone = jsonObject.getString("phone");
        teacherDao.editInfo(touxiang,username,words,introduce,phone);
        Teacher teacher = teacherDao.findByUserPhone(phone);
        return teacher;
    }

    @Override
    public void register(JSONObject jsonObject, HttpSession httpSession) {
        /*
         * 1.根据用户查询数据库是否存在该用户
         * 2.如果存在则报错：用户已经存在
         * 3.如果不存在，进行注册
         * */
        String messageVerify = jsonObject.getString("message");
        // 从session中获取发送的验证码
        JSONObject userCode = (JSONObject) httpSession.getAttribute("code");
        if(ObjectUtils.isEmpty(userCode)){
            throw new RuntimeException("请获取验证码");
        }

        String phone = jsonObject.getString("phone");
        String codePhone = userCode.getString("phone");
        String code = userCode.getString("code");
        Long time = userCode.getLong("createTime");
        Long diff = System.currentTimeMillis() - time;

        if(!phone.equals(codePhone)){
            throw new RuntimeException("请重新获取验证码");
        }
        // 判断验证码是否有效（5分钟）
        if(diff / 1000 /60 > 5){
            throw new RuntimeException("验证码失效，请重新获取");
        }
        // 判断验证码是否相等
        if(!messageVerify.equals(code)){
            throw new RuntimeException("验证码不正确，请重新输入");
        }

        String realname = jsonObject.getString("realname");
        String password = jsonObject.getString("password");
        // 注册之前对密码进行加密
        String passwordSecret = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        String username = jsonObject.getString("username");
        String idcard = jsonObject.getString("idcard");

        Teacher teacher = new Teacher(username,realname,passwordSecret,phone,idcard);

        // 查找用户是否存在
        Teacher teacherDB = teacherDao.findByUserPhone(phone);
        if(!ObjectUtils.isEmpty(teacherDB)){
            throw new RuntimeException("该用户已被注册");
        }

        // 注册
        teacherDao.save(teacher);
    }

    @Override
    public PageResult teacherAll(Integer page, Integer pageSize) {
        Integer pageNum = (page - 1) * pageSize;
        List<Teacher> teacherList =  teacherDao.teacherAll(pageNum,pageSize);
        Integer total = teacherDao.teacherAllCount();

        PageResult pageResult = new PageResult(pageNum,pageSize,total,teacherList);
        return pageResult;
    }

    @Override
    public PageResult search(JSONObject jsonObject) {
        String input = jsonObject.getString("input");
        Integer page = jsonObject.getInteger("page");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Integer pageNum = (page - 1) * pageSize;
        List<Teacher> teacherList =  teacherDao.search(input,pageNum,pageSize);
        Integer total = teacherDao.searchCount(input);
        PageResult pageResult = new PageResult(pageNum,pageSize,total,teacherList);
        return pageResult;
    }

    @Override
    public void delete(Integer id) {
        teacherDao.delete(id);
    }

    @Override
    public void export(HttpServletResponse response) throws Exception {
        List<Teacher> teacherList = teacherDao.export();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("id", "学生ID");
        writer.addHeaderAlias("realname", "真实姓名");
        writer.addHeaderAlias("username", "用户昵称");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("idcard", "身份证号");
        writer.addHeaderAlias("touxiang", "头像地址");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("introduce", "简介");
        writer.addHeaderAlias("words", "科目");
        writer.addHeaderAlias("job", "身份");
        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(teacherList, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("老师名单","UTF-8");

        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    @Override
    public void imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<List<Object>> list = reader.read(1);
        for (List<Object> row : list) {
            Teacher teacher = new Teacher();
            String username = row.get(0).toString();
            teacher.setUsername(row.get(0).toString());
            teacher.setRealname(row.get(1).toString());
            String phone = row.get(2).toString();
            teacher.setPhone(row.get(2).toString());
            String password = row.get(3).toString();
            String passwordSecret = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
            teacher.setPassword(passwordSecret);
            teacher.setIdcard(row.get(4).toString());
            Teacher teacherDB = teacherDao.findByUserPhone(phone);
            if(!ObjectUtils.isEmpty(teacherDB)){
                throw new RuntimeException(username + "老师已存在");
            }
            teacherDao.save(teacher);
        }

    }
}
