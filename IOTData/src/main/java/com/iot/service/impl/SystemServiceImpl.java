package com.iot.service.impl;

import com.github.pagehelper.PageHelper;
import com.iot.dao.SystemMapper;
import com.iot.model.Systems;
import com.iot.model.acceptParam.SystemParam;
import com.iot.model.resultAndPage.PageResultBean;
import com.iot.service.SystemService;
import com.iot.service.UserService;
import com.iot.util.exception.DescribeException;
import com.iot.util.exception.ExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private UserService userService;

    @Override
    public List<Systems> queryUserSystemInformationByUserId(int userId) {
        return systemMapper.queryUserSystemInformationByUserId(userId);
    }

    @Override
    public Object queryOneSystemInformation(Integer systemId){
        return systemMapper.selectByPrimaryKey(systemId);
    }

    @Override
    public Object queryAllSystemInformation_Admin(Integer pageNum, Integer pageSize, String orderBy, String sort) {
        String order = orderBy + " " + sort;
        PageHelper.startPage(pageNum, pageSize, order);
        return new PageResultBean<Systems>(systemMapper.queryAllSystemInformation_Admin());
    }

    @Override
    public synchronized Object insertSystem(SystemParam systemParam) {
        if(systemParam.getDirection()==null||systemParam.getOperation()==null||systemParam.getName()==null){
            throw new DescribeException(ExceptionEnum.SYSTEM_INFORMATION_INCOMPLETE);
        }
        systemMapper.insertSelective(systemParam);
        return systemMapper.queryUserSystemInformationByUserId(systemParam.getUserId());
    }

    @Override
    public Object updateSystemInformation(SystemParam systemParam) {
        return systemMapper.updateByPrimaryKeySelective(systemParam);
    }

    @Override
    public Object deleteSystem(int systemId) {
        return systemMapper.deleteByPrimaryKey(systemId);
    }

    @Override
    public Systems verifySystemInUser(int userId, int systemId){
        Systems systems =systemMapper.verifySystemInUser(userId,systemId);
        return systems;
    }
}
