package com.iot.util.exception;

public enum ExceptionEnum {
    SUCCESS(0, "成功"),

    LOGIN_ERROR(101, "登陆失败,手机或密码错误"),
    USER_NOT_FOUND(102, "用户不存在"),
    USER_EXIST(103, "用户已经注册"),
    INCOMPLETE_REGISTRATION_INFORMATION(104, "注册信息不完整"),
    ROLE_SELECT_ERROR(105,"角色选择必须是“用户”或者“管理员”"),
    PASSWORD_ERRPOR(106,"密码错误"),

    NEED_LOGIN(201, "没有token，请登陆后操作"),
    TOKEN_OUTTIME(202, "token已过期，有效期1天"),
    NEED_ADMIN_ROLES(203, "你没有管理员权限"),

    METHOD_FAILED(301, "请求方法错误，请尝试其他请求方法"),
    PARAMETER_ERROR(302, "上传参数不正确或不完整，请重新请求"),
    SORT_ERROR(302, "排序字段错误"),
    DATABASE_ERROR(304,"数据库外键冲突"),

    SYSTEM_INFORMATION_INCOMPLETE(501,"系统信息不完整"),
    SYSTEM_USER_ERROR(502,"此系统和用户不匹配"),
    NEED_SYSTEMID(503,"需要系统id"),
    NEED_SENSORID(504,"需要传感器id"),
    SENSOR_NOT_EXIST(505,"此传感器不存在"),
    SENSOR_USER_ERROR(506,"此传感器和当前用户不匹配"),
    SENSOR_INFORMATION_INCOMPLETE(507,"传感器信息不完整"),
    NEED_DEVICEID(508,"需要设备id"),
    DEVICE_NOT_EXIST(509,"此设备不存在"),
    DEVICE_USER_ERROR(510,"此设备和当前用户不匹配"),
    DEVICE_INFORMATION_INCOMPLETE(511,"设备信息不完整"),

    UNKNOW_ERROR(100, "未知错误"),
    ;

    private Integer code;

    private String msg;

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
