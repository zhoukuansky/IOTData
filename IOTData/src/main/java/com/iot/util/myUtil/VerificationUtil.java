package com.iot.util.myUtil;

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

    public static void sortVerification(String sort) throws Exception{
        if (sort.equals("ASC")||sort.equals("DESC")){
            return;
        }else {
            throw new DescribeException(ExceptionEnum.SORT_ERROR);
        }
    }
}
