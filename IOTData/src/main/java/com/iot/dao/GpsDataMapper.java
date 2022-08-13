package com.iot.dao;

import com.iot.model.GpsData;
import com.iot.model.acceptParam.DataConditionParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GpsDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GpsData record);

    int insertSelective(GpsData record);

    GpsData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GpsData record);

    int updateByPrimaryKey(GpsData record);

    List<GpsData> queryGpsDataBySensorId(DataConditionParam dataConditionParam);

    void deleteGpsDatas(int[] ids);
}