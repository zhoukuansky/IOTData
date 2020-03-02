package com.iot.dao;

import com.iot.model.NumData;
import org.springframework.stereotype.Repository;

@Repository
public interface NumDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NumData record);

    int insertSelective(NumData record);

    NumData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NumData record);

    int updateByPrimaryKey(NumData record);
}