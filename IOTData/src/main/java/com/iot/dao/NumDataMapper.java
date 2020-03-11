package com.iot.dao;

import com.iot.model.NumData;
import com.iot.model.acceptParam.DataConditionParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NumDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NumData record);

    int insertSelective(NumData record);

    NumData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NumData record);

    int updateByPrimaryKey(NumData record);

    List<NumData> queryNumDataBySensorId(DataConditionParam dataConditionParam);

    void insertNumDatas(int sensorId, double[] values);

    void deleteNumDatas(int[] ids);
}