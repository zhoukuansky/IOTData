package com.iot.service;

import com.iot.model.Device;
import com.iot.model.acceptParam.DeviceParam;

public interface DeviceService {
    Object queryDeviceBySystemId(Integer systemId);

    Object queryDeviceByUserId(int userId);

    Object queryAllDevice_Admin(Integer pageNum, Integer pageSize, String orderBy, String sort);

    Object insertDevice(DeviceParam deviceParam);

    Object updateDevice(DeviceParam deviceParam);

    Object deleteDevice(int deviceId);

    Device verifyDeviceInUser(int deviceId);
}
