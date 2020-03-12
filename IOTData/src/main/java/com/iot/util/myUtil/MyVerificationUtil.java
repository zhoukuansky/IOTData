package com.iot.util.myUtil;

import com.iot.model.Device;
import com.iot.model.Sensor;
import com.iot.model.Systems;
import com.iot.model.User;
import com.iot.service.DeviceService;
import com.iot.service.SensorService;
import com.iot.service.SystemService;
import com.iot.service.UserService;
import com.iot.util.exception.DescribeException;
import com.iot.util.exception.ExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 自己编写的简单验证权限工具
 *
 * @author HP
 * @date
 */
@Component
public class MyVerificationUtil {
    @Autowired
    private SystemService systemService;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 验证管理员权限
     * 传参tokenData
     */
    public void adminVerification(Map tokenData) throws Exception {
        String role = (String) tokenData.get("role");
        if (!role.equals("管理员")) {
            throw new DescribeException(ExceptionEnum.NEED_ADMIN_ROLES);
        }
        return;
    }

    /**
     * 验证系统是否在此用户名下
     * 传参userId、systemId
     */
    public void verifySystemInUser(int userId, int systemId) throws Exception {
        if (systemId == 0) {
            throw new DescribeException(ExceptionEnum.NEED_SYSTEMID);
        }
        Systems systems = systemService.verifySystemInUser(userId, systemId);
        if (systems == null) {
            throw new DescribeException(ExceptionEnum.SYSTEM_USER_ERROR);
        }
        return;
    }

    /**
     * 验证传感器是否在此用户名下
     * 传参userId、sensorId
     */
    public void verifySensorInUser(int userId, int sensorId) throws Exception {
        if (sensorId == 0) {
            throw new DescribeException(ExceptionEnum.NEED_SENSORID);
        }
        Sensor sensor = sensorService.querySensorBySensorId(sensorId);
        if (sensor == null) {
            throw new DescribeException(ExceptionEnum.SENSOR_NOT_EXIST);
        }
        Systems systems = systemService.verifySystemInUser(userId, sensor.getSystemId());
        if (systems == null) {
            throw new DescribeException(ExceptionEnum.SENSOR_USER_ERROR);
        }
        return;
    }

    /**
     * 验证设备是否在此用户名下
     * 传参userId、deviceId
     */
    public void verifyDeviceInUser(int userId, int deviceId) {
        if (deviceId == 0) {
            throw new DescribeException(ExceptionEnum.NEED_DEVICEID);
        }
        Device device = deviceService.verifyDeviceInUser(deviceId);
        if (device == null) {
            throw new DescribeException(ExceptionEnum.DEVICE_NOT_EXIST);
        }
        Systems systems = systemService.verifySystemInUser(userId, device.getSystemId());
        if (systems == null) {
            throw new DescribeException(ExceptionEnum.DEVICE_USER_ERROR);
        }
    }

    /**
     * 验证密码是否正
     * 传参password、id
     */
    public void verifyPassword(String password, int userId) throws Exception {
        User user = userService.verifyPassword(password, userId);
        if (user == null) {
            throw new DescribeException(ExceptionEnum.PASSWORD_ERRPOR);
        }
    }

    /**
     * 验证排序方式输入是否正确
     * 传参sort
     */
    public void sortVerification(String sort) throws Exception {
        if (sort.equals("ASC") || sort.equals("DESC")) {
            return;
        } else {
            throw new DescribeException(ExceptionEnum.SORT_ERROR);
        }
    }

    /**
     * 验证ApiKey是否和sensorId相对应
     * 传参ApiKey
     */
    public User verifyApiKeyInUserLinkSensorId(String apiKey, int sensorId) throws Exception {
        if (apiKey == null) {
            throw new DescribeException(ExceptionEnum.APIKey_NOT_NULL);
        }
        User user = userService.verifyApiKey(apiKey);
        if (user == null) {
            throw new DescribeException(ExceptionEnum.APIKey_NOT_EXIST);
        }
        verifySensorInUser(user.getId(), sensorId);
        return user;
    }

    /**
     * 验证邮箱是否已经存在
     * 传参email
     */
    public void verifyEmailExit(String email) throws Exception {
        User user = userService.queryUserByEmail(email);
        if (null != user) {
            throw new DescribeException(ExceptionEnum.USER_EXIST);
        }
        return;
    }

    /**
     * 验证邮箱验证码是否过时
     * 传参email
     */
    public void verifyEmailOutTime(String email) throws Exception {
        String emailKey = "EmailCache::" + email;
        String code = (String) redisTemplate.opsForValue().get(emailKey);
        if (null != code) {
            throw new DescribeException(ExceptionEnum.EMAIL_CODE_EXIT);
        }
        return;
    }

    /**
     * 验证邮箱验证码
     * 传参email
     */
    public void verifyEmialCode(String email, String emailCode) throws Exception {
        String emailKey = "EmailCache::" + email;
        String code = (String) redisTemplate.opsForValue().get(emailKey);

        if (!emailCode.equals(code)) {
            throw new DescribeException(ExceptionEnum.EMAIL_CODE_ERROR);
        }
        redisTemplate.delete(emailKey);
        return;
    }

    /**
     * 验证手机是否已经存在
     * 传参tel
     */
    public void verifyTelExit(String tel) {
        User user = userService.queryUserByTel(tel);
        if (null != user) {
            throw new DescribeException(ExceptionEnum.TEL_EXIST);
        }
        return;
    }

    /**
     * 验证所传数据类型和传感器类型是否匹配
     * 传参sensorId、type
     */
    public void verifySensorType(int sensorId, String type) {
        Sensor sensor = sensorService.querySensorBySensorId(sensorId);
        if (sensor == null) {
            throw new DescribeException(ExceptionEnum.SENSOR_NOT_EXIST);
        }
        if (!sensor.getDataType().equals(type)) {
            throw new DescribeException(ExceptionEnum.DATA_SENSOR_ERROR);
        }
        return;
    }
}
