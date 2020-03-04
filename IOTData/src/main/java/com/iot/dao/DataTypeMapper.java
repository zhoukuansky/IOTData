package com.iot.dao;

import com.iot.model.DataType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(String word);

    int insertSelective(DataType record);

    DataType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DataType record);

    int updateByPrimaryKey(DataType record);

    List<DataType> queryAllDataType();

    int insertDataType_Admin(String word);

    int deleteDataType_Admin(int[] ids);
}