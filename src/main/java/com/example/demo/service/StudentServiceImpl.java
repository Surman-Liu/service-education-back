package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.StudentDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

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
        System.out.println(JSONObject.toJSONString(student));
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
}
