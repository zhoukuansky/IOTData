package com.iot.dao;

import com.iot.model.ImgData;
import com.iot.model.acceptParam.DataConditionParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImgDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ImgData record);

    int insertSelective(ImgData record);

    List<ImgData> selectByPrimaryKey(int[] ids);

    int updateByPrimaryKeySelective(ImgData record);

    int updateByPrimaryKeyWithBLOBs(ImgData record);

    int updateByPrimaryKey(ImgData record);

    List<ImgData> queryImgBySensorIdAndTime(DataConditionParam dataConditionParam);

    void deleteImgs(int[] ids);

    List<ImgData> queryImgBySensorId(int sensorId);
}