package com.iot.util.exception;

public enum ExceptionEnum {
    SUCCESS(0, "成功"),

    LOGIN_ERROR(101, "登陆失败,手机或密码错误"),
    USER_NOT_FOUND(102, "用户不存在"),
    USER_EXIST(103, "用户已经注册"),
    ILLEGAL_ARGUMENT(104, "角色异常"),
    UPDATE_ERROR(105, "更新用户信息失败"),
    UPDATE_PASSWORD_ERROR(106, "更新用户密码失败"),
    INCOMPLETE_REGISTRATION_INFORMATION(107, "注册信息不完整"),
    ROLE_SELECT_ERROR(108,"角色选择必须是“用户”或者“管理员”"),
    PASSWORD_ERRPOR(109,"密码错误"),

    NEED_LOGIN(201, "没有token，请登陆后操作"),
    TOKEN_OUTTIME(202, "token已过期，有效期1天"),
    NEED_ADMIN_ROLES(203, "你没有管理员权限"),

    METHOD_FAILED(301, "请求方法错误，请尝试其他请求方法"),
    PARAMETER_ERROR(302, "上传参数不正确或不完整，请重新请求"),
    SORT_ERROR(302, "排序字段错误"),

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
