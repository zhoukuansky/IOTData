package com.iot.dao;

import com.iot.model.Direction;
import com.iot.model.Operation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(String word);

    int insertSelective(Operation record);

    Operation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Operation record);

    int updateByPrimaryKey(Operation record);

    List<Direction> queryAllOperationType();

    int deleteOperationType_Admin(int[] ids);
}