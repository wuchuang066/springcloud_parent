package com.vecher.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.vecher.sms.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 监听器  监听sms消息队列中是否有消息要消费
 * @description :
 **/
@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @RabbitHandler
    public void sendSms(Map<String, String> message) {
        smsUtil.sendSms(message.get("mobile"), message.get("code") );
    }
}
