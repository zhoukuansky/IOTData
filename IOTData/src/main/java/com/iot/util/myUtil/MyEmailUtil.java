package com.iot.util.myUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 验证邮箱
 */
@Component
public class MyEmailUtil {
    @Autowired
    private MyStringUtil myStringUtil;
    @Autowired
    JavaMailSenderImpl mailSender;

    private String myFrom="zhoukuansky@163.com";
    private String subject="IOTData！您的物联网数据管家！";
    private String text="欢迎来到IOTData:本站临时网址为：" +
            "<a href=\"http://114.115.243.22/IOTData/swagger-ui.html\">" +
            "http://114.115.243.22/IOTData/swagger-ui.html#" +
            "</a>" +
            "<br><br>本次验证码有效期为3分钟，请注意时间。" +
            "<br><br>           您进行的本次验证服务，验证码为:  ";

    @Cacheable(value = "EmailCache",key = "#email")
    public String sendMail(String email)throws MessagingException {
        String code=myStringUtil.createEmailVerifyCode();

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
        message.setFrom(myFrom);
        message.setSubject(subject);
        message.setText(text+code,true);
        message.setTo(email);

        //465端口是为SMTPS（SMTP-over-SSL）协议服务开放的，这是SMTP协议基于SSL安全协议之上的一种变种协议。
        Properties props = new Properties();
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        mailSender.setJavaMailProperties(props);

        try {
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            System.out.println("发送邮件时发生异常！"+e);
        }
        return code;
    }
}
