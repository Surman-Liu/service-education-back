package com.example.demo.service;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.StudentDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.*;
import com.example.demo.entity.Class;
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
public class StudentServiceImpl implements StudentService{
    private StudentDao studentDao;
    @Autowired
    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Student login(String phone, String password) {
        Student student = studentDao.findByUserPhone(phone);
        if(ObjectUtils.isEmpty(student)){
            throw  new RuntimeException("该用户不存在");
        }
        String passwordSecret = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if(!student.getPassword().equals(passwordSecret)){
            throw new RuntimeException("密码输入错误");
        }
        return student;
    }

    @Override
    public Student messageLogin(String phone, String code,HttpSession httpSession) {
        // 查找用户是否存在
        Student studentDB = studentDao.findByUserPhone(phone);
        if(ObjectUtils.isEmpty(studentDB)){
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

        return studentDB;
    }

    @Override
    public void forgetPassword(String phone, String code, String newPassword, HttpSession httpSession) {
        this.messageLogin(phone,code,httpSession);
        String passwordSecret = DigestUtils.md5DigestAsHex(newPassword.getBytes(StandardCharsets.UTF_8));
        studentDao.updatePassword(phone,passwordSecret);
    }

    @Override
    public Student editInfo(JSONObject jsonObject) {
        String touxiang = jsonObject.getString("touxiang");
        String username = jsonObject.getString("username");
        String words = jsonObject.getString("words");
        String introduce = jsonObject.getString("introduce");
        String phone = jsonObject.getString("phone");
        System.out.println(phone);
        studentDao.editInfo(touxiang,username,words,introduce,phone);
        Student student = studentDao.findByUserPhone(phone);
        return student;
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

        Student student = new Student(username,realname,passwordSecret,phone);

        // 查找用户是否存在
        Student studentDB = studentDao.findByUserPhone(phone);
        if(!ObjectUtils.isEmpty(studentDB)){
            throw new RuntimeException("该用户已被注册");
        }

        // 注册
        studentDao.save(student);
    }

    @Override
    public PageResult selectedClass(JSONObject jsonObject) {
        Integer student_id = jsonObject.getInteger("student_id");
        Integer class_type = jsonObject.getInteger("class_type");
        Integer page = jsonObject.getInteger("page");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Integer pageNum = (page - 1) * pageSize;
        List<Class> classList =  studentDao.selectedClass(student_id,class_type,pageNum,pageSize);
        Integer total = studentDao.total(student_id,class_type);

        PageResult pageResult = new PageResult(pageNum,pageSize,total,classList);
        return pageResult;
    }

    @Override
    public TypeNum typeCount(Integer student_id) {
        Integer classes = studentDao.typeCount(student_id,1);
        Integer exam = studentDao.typeCount(student_id,2);
        Integer trail = studentDao.typeCount(student_id,3);

        TypeNum typeNum = new TypeNum(classes,exam,trail);
        return typeNum;
    }

    @Override
    public PageResult studentAll(Integer page, Integer pageSize) {
        Integer pageNum = (page - 1) * pageSize;
        List<Student> studentList =  studentDao.studentAll(pageNum,pageSize);
        Integer total = studentDao.studentAllCount();

        PageResult pageResult = new PageResult(pageNum,pageSize,total,studentList);
        return pageResult;
    }

    @Override
    public PageResult search(JSONObject jsonObject) {
        String input = jsonObject.getString("input");
        Integer page = jsonObject.getInteger("page");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Integer pageNum = (page - 1) * pageSize;
        List<Student> studentList =  studentDao.search(input,pageNum,pageSize);
        Integer total = studentDao.searchCount(input);
        PageResult pageResult = new PageResult(pageNum,pageSize,total,studentList);
        return pageResult;
    }

    @Override
    public void delete(Integer id) {
        studentDao.delete(id);
    }

    @Override
    public void export(HttpServletResponse response) throws Exception {
        List<Student> studentList = studentDao.export();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("id", "学生ID");
        writer.addHeaderAlias("realname", "真实姓名");
        writer.addHeaderAlias("username", "用户昵称");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("touxiang", "头像地址");
        writer.addHeaderAlias("introduce", "简介");
        writer.addHeaderAlias("words", "科目");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("job", "身份");
        writer.addHeaderAlias("classList", "身份");
        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(studentList, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("学生名单","UTF-8");

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
            Student student = new Student();
            String username = row.get(0).toString();
            student.setUsername(row.get(0).toString());
            student.setRealname(row.get(1).toString());
            String phone = row.get(2).toString();
            student.setPhone(row.get(2).toString());
            String password = row.get(3).toString();
            String passwordSecret = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
            student.setPassword(passwordSecret);
            Student studentDB = studentDao.findByUserPhone(phone);
            if(!ObjectUtils.isEmpty(studentDB)){
                throw new RuntimeException(username + "学生已存在");
            }
            studentDao.save(student);
        }

    }
}
