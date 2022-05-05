package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.AdminDao;
import com.example.demo.entity.Admin;
import com.example.demo.entity.PageResult;
import com.example.demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{
    private AdminDao adminDao;
    @Autowired
    public AdminServiceImpl(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public Admin login(String phone, String password) {
        Admin admin = adminDao.findByUserPhone(phone);
        if(ObjectUtils.isEmpty(admin)){
            throw  new RuntimeException("该用户不存在");
        }
        String passwordSecret = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if(!admin.getPassword().equals(passwordSecret)){
            throw new RuntimeException("密码输入错误");
        }
        return admin;
    }

    @Override
    public Admin messageLogin(String phone, String code, HttpSession httpSession) {
        // 查找用户是否存在
        Admin admin = adminDao.findByUserPhone(phone);
        if(ObjectUtils.isEmpty(admin)){
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

        return admin;
    }

    @Override
    public void forgetPassword(String phone, String code, String newPassword, HttpSession httpSession) {
        this.messageLogin(phone,code,httpSession);
        String passwordSecret = DigestUtils.md5DigestAsHex(newPassword.getBytes(StandardCharsets.UTF_8));
        adminDao.updatePassword(phone,passwordSecret);
    }

    @Override
    public PageResult adminAll(Integer page, Integer pageSize) {
        Integer pageNum = (page - 1) * pageSize;
        List<Admin> adminList =  adminDao.adminAll(pageNum,pageSize);
        Integer total = adminDao.adminAllCount();

        PageResult pageResult = new PageResult(pageNum,pageSize,total,adminList);
        return pageResult;
    }

    @Override
    public PageResult search(JSONObject jsonObject) {
        String input = jsonObject.getString("input");
        Integer page = jsonObject.getInteger("page");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Integer pageNum = (page - 1) * pageSize;
        List<Admin> adminList =  adminDao.search(input,pageNum,pageSize);
        Integer total = adminDao.searchCount(input);
        PageResult pageResult = new PageResult(pageNum,pageSize,total,adminList);
        return pageResult;
    }

    @Override
    public void delete(Integer id) {
        adminDao.delete(id);
    }


}
