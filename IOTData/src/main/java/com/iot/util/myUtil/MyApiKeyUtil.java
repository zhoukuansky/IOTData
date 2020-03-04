package com.iot.util.myUtil;

import com.iot.model.User;
import com.iot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 生成唯一字符串工具
 * @author HP
 * @date
 */
@Component
public class MyApiKeyUtil {
    @Autowired
    private UserService userService;

    /**
     * 生成随机字符串
     *
     * @param stringLength:生成的字符串长度
     * @return
     */
    public String createApiKey() {
        String string = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuffer key = new StringBuffer();
        int keyLen=22;
        for (int i = 0; i < keyLen; i++) {
            //向下取整0-25
            int index = (int) Math.floor(Math.random() * string.length());
            key.append(string.charAt(index));
        }
        String result="IOTData_"+key.toString();
        if(isExist(result)){
            createApiKey();
        }
        return result;
    }
    /**
     * 验证生成的字符串是否已存在
     *
     * @param stringLength:生成的字符串长度
     * @return
     */
    public boolean isExist(String result){
        User user=userService.verifyApiKey(result);
        if (user!=null){
            return true;
        }
        return false;
    }
}
