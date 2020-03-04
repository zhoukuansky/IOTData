package com.iot.dao;

import com.iot.model.Systems;
import com.iot.model.acceptParam.SystemParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Systems record);

    int insertSelective(SystemParam systemParam);

    Systems selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemParam systemParam);

    int updateByPrimaryKey(Systems record);

    List<Systems> queryUserSystemInformationByUserId(int userId);

    List<Systems> queryAllSystemInformation_Admin();

    Systems verifySystemInUser(int userId, int systemId);
}