package com.vecher.sms.util;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 短信工具类
 * @author Administrator
 *
 */
@Component
public class SmsUtil {

    @Autowired
    private Environment env;

    public  void sendSms(String phone,String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", env.getProperty("aliyun.sms.accessKeyId"), env.getProperty("aliyun.sms.accesskeySecret"));
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        //电话号码
        request.putQueryParameter("PhoneNumbers", phone);
        //签名
        request.putQueryParameter("SignName", env.getProperty("aliyun.sms.signName"));
        //模板
        request.putQueryParameter("TemplateCode", env.getProperty("aliyun.sms.templateCode"));
        //验证码
        request.putQueryParameter("TemplateParam", "{\"code\":"+code+"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}