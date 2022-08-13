package com.iot.dao;

import com.iot.model.Direction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(String word);

    int insertSelective(Direction record);

    Direction selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Direction record);

    int updateByPrimaryKey(Direction record);

    List<Direction> queryAllDirectionType();

    int deleteDirectionType_Admin(int[] ids);
}