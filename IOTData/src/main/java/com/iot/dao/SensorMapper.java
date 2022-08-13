package com.iot.dao;

import com.iot.model.Sensor;
import com.iot.model.acceptParam.SensorParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Sensor record);

    int insertSelective(SensorParam sensorParam);

    Sensor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SensorParam sensorParam);

    int updateByPrimaryKey(Sensor record);

    List<Sensor> querySensorBySystemId(Integer systemId);

    List<Sensor> queryAllSensor_Admin();
}