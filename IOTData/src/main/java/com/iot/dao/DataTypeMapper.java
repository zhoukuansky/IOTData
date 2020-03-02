package com.iot.dao;

import com.iot.model.DataType;
import org.springframework.stereotype.Repository;

@Repository
public interface DataTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DataType record);

    int insertSelective(DataType record);

    DataType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DataType record);

    int updateByPrimaryKey(DataType record);
}