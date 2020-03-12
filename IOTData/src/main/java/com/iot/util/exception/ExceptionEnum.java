package com.iot.util.exception;

public enum ExceptionEnum {
    SUCCESS(1, "成功"),

    //用户信息相关
    LOGIN_ERROR(101, "登陆失败,邮箱或密码错误"),
    USER_NOT_FOUND(102, "用户不存在"),
    USER_EXIST(103, "用户(邮箱)已经注册"),
    TEL_EXIST(104, "手机号码已经存在"),
    PASSWORD_ERRPOR(105, "密码错误"),

    //token相关
    NEED_LOGIN(201, "没有token，请登陆后操作"),
    TOKEN_OUTTIME(202, "token已过期，有效期1天"),
    NEED_ADMIN_ROLES(203, "你没有管理员权限"),

    //请求方法相关
    METHOD_FAILED(301, "请求方法错误，请尝试其他请求方法"),
    PARAMETER_ERROR(302, "上传参数不正确或不完整，请重新请求"),
    SORT_ERROR(302, "排序字段错误"),
    DATABASE_ERROR(304, "数据库外键冲突"),

    //系统、传感器、设备相关
    SYSTEM_INFORMATION_INCOMPLETE(501, "系统信息不完整"),
    SYSTEM_USER_ERROR(502, "此系统和用户不匹配"),
    NEED_SYSTEMID(503, "需要系统id"),
    NEED_SENSORID(504, "需要传感器id"),
    SENSOR_NOT_EXIST(505, "此传感器不存在"),
    SENSOR_USER_ERROR(506, "此传感器和当前用户不匹配"),
    SENSOR_INFORMATION_INCOMPLETE(507, "传感器信息不完整"),
    NEED_DEVICEID(508, "需要设备id"),
    DEVICE_NOT_EXIST(509, "此设备不存在"),
    DEVICE_USER_ERROR(510, "此设备和当前用户不匹配"),
    DEVICE_INFORMATION_INCOMPLETE(511, "设备信息不完整"),

    //apiKey相关
    APIKey_NOT_EXIST(601, "此apiKey不存在"),
    APIKey_NOT_NULL(602, "请输入apiKey"),

    //验证码相关
    EMAIL_CODE_EXIT(701, "验证码服务有效期三分钟，请勿在三分钟内重复进行"),
    EMAIL_CODE_ERROR(702, "验证码过期或错误"),

    //数据点相关
    DATA_SENSOR_ERROR(801, "数据类型和传感器类型不匹配"),
    IMAGE_NOT_NULL(802, "图片文件为空，请重新上传"),
    UPLOAD_IMG_ERROR(803, "保存图片失败"),
    DELETE_IMG_ERROR(804, "删除图片失败"),


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
