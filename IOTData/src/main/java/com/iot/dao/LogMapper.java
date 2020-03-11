package com.iot.dao;

import com.iot.model.Log;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Log record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);

    List<Log> queryAllLog_Admin();

    List<Log> queryOneUserLog(int userId);

    int deleteOneUserLog(int[] ids,int userId);

    int deleteLog_Admin(int[] ids);
}