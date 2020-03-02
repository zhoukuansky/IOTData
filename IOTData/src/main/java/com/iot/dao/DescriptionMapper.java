package com.iot.dao;

import com.iot.model.Description;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Description record);

    int insertSelective(Description record);

    Description selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Description record);

    int updateByPrimaryKey(Description record);
}