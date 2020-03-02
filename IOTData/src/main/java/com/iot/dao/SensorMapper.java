package com.iot.dao;

import com.iot.model.Sensor;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Sensor record);

    int insertSelective(Sensor record);

    Sensor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Sensor record);

    int updateByPrimaryKey(Sensor record);
}