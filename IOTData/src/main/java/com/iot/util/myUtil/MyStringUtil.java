package com.iot.util.myUtil;

import com.iot.model.User;
import com.iot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 生成唯一字符串工具
 *
 * @author HP
 * @date
 */
@Component
public class MyStringUtil {
    private final String SYMBOLS = "0123456789";
    private final Random RANDOM = new SecureRandom();
    @Autowired
    private UserService userService;

    /**
     * 生成6位随机验证码
     *
     * @return 返回6位数字验证码
     */
    public String createEmailVerifyCode() {
        char[] nonceChars = new char[6];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }


    /**
     * 生成随机字符串
     *
     * @param:
     * @return
     */
    public String createApiKey() {
        String string = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuffer key = new StringBuffer();
        int keyLen = 22;
        for (int i = 0; i < keyLen; i++) {
            //向下取整0-25
            int index = (int) Math.floor(Math.random() * string.length());
            key.append(string.charAt(index));
        }
        String result = "IOTData_" + key.toString();
        if (isExist(result)) {
            createApiKey();
        }
        return result;
    }

    /**
     * 验证生成的字符串是否已存在
     *
     * @param:
     * @return
     */
    public boolean isExist(String result) {
        User user = userService.verifyApiKey(result);
        if (user != null) {
            return true;
        }
        return false;
    }
}
