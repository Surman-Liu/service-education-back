package com.example.demo.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URLEncoder;
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

    @Override
    public void add(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String phone = jsonObject.getString("phone");
        String password = jsonObject.getString("password");
        Admin admin = adminDao.findByUserPhone(phone);
        if(!ObjectUtils.isEmpty(admin)){
            throw new RuntimeException("该管理员已存在");
        }
        String passwordSecret = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        adminDao.add(username,phone,passwordSecret);
    }

    @Override
    public void export(HttpServletResponse response) throws Exception {
        List<Admin> adminList = adminDao.export();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("id", "管理员ID");
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("touxiang", "头像地址");
        writer.addHeaderAlias("job", "身份");
        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(adminList, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("管理员名单","UTF-8");

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
            String username = row.get(0).toString();
            String password = row.get(2).toString();
            String passwordSecret = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
            String phone = row.get(1).toString();
            Admin admin = adminDao.findByUserPhone(phone);
            if(!ObjectUtils.isEmpty(admin)){
                throw new RuntimeException(username + "管理员已存在");
            }
            adminDao.add(username,phone,passwordSecret);
        }

    }
}
