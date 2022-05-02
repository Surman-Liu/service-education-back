package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.StudentDao;
import com.example.demo.dao.TeacherDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.Student;
import com.example.demo.entity.Teacher;
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
}
