package com.iot.util.authentication;

import com.iot.util.exception.DescribeException;
import com.iot.util.exception.ExceptionEnum;

import java.util.Map;

/**
 * 自己编写的简单验证权限工具
 * @author HP
 * @date
 */
public class VerificationUtil {

    public static void adminVerification(Map tokenData) throws Exception{
        String role = (String) tokenData.get("role");
        if (!role.equals("管理员")) {
            throw new DescribeException(ExceptionEnum.NEED_ADMIN_ROLES);
        }
        return;
    }

    public static int judgementUserId(Map tokenData,int userId) throws Exception{
        return 0;
    }
}
