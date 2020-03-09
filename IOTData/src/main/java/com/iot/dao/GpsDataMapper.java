package com.iot.dao;

import com.iot.model.GpsData;
import org.springframework.stereotype.Repository;

@Repository
public interface GpsDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GpsData record);

    int insertSelective(GpsData record);

    GpsData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GpsData record);

    int updateByPrimaryKey(GpsData record);
}