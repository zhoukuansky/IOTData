package com.iot.dao;

import com.iot.model.Operation;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Operation record);

    int insertSelective(Operation record);

    Operation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Operation record);

    int updateByPrimaryKey(Operation record);
}