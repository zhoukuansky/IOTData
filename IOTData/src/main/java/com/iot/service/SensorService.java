package com.iot.service;

import com.iot.model.Sensor;
import com.iot.model.acceptParam.SensorParam;

public interface SensorService {

    Object querySensorBySystemId(Integer systemId);

    Object querySensorByUserId(int userId);

    Object queryAllSensor_Admin(Integer pageNum, Integer pageSize, String orderBy, String sort);

    Object insertSensor(SensorParam sensorParam);


    Object updateSensor(SensorParam sensorParam);

    Object deleteSensor(int sensorId);

    Sensor verifySensorInUser(int sensorId);
}
