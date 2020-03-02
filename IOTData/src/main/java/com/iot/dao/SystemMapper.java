package com.iot.dao;

import com.iot.model.System;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(System record);

    int insertSelective(System record);

    System selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(System record);

    int updateByPrimaryKey(System record);
}