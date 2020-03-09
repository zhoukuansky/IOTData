package com.iot.dao;

import com.iot.model.ImgData;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ImgData record);

    int insertSelective(ImgData record);

    ImgData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ImgData record);

    int updateByPrimaryKeyWithBLOBs(ImgData record);

    int updateByPrimaryKey(ImgData record);
}