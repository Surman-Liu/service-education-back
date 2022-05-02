package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.zhenzi.sms.ZhenziSmsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Transactional
public class UtilsServiceImpl implements UtilsService{
    /**
     * 平台中提供的 appid 和 appsecret
     */
    @Value("${sms.verific.appid}")
    public String APPID;
    @Value("${sms.verific.appsecret}")
    public String APPKEY;
    @Value("${sms.verific.apiurl}")
    public String URL;
    @Value("${sms.verific.template")
    public String TEMPLATE;

    @Override
    public Boolean sendCode(String phone, HttpSession httpSession) throws Exception {
        ZhenziSmsClient client = new ZhenziSmsClient(URL, APPID, APPKEY);

        // 生成6位随机验证码
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);

        // 将验证码存入Session中
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone",phone);
        jsonObject.put("code",verifyCode);
        jsonObject.put("createTime",System.currentTimeMillis());
        httpSession.setAttribute("code",jsonObject);

        // 发送短信
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("number", phone);
        params.put("templateId", "2945");
        String[] templateParams = new String[2];
        templateParams[0] = verifyCode;
        templateParams[1] = "5分钟";
        params.put("templateParams", templateParams);
        String result = client.send(params);

        //获取短信结果
        JSONObject resultJson = JSONObject.parseObject(result);
        System.out.println(resultJson.getString("data"));
        if(resultJson.getInteger("code") == 0){
            return true;
        }else{
            return false;
        }
    }
}
