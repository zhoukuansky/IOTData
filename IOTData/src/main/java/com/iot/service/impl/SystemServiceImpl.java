package com.iot.service.impl;

import com.github.pagehelper.PageHelper;
import com.iot.dao.SystemMapper;
import com.iot.model.Sensor;
import com.iot.model.Systems;
import com.iot.model.acceptParam.SystemParam;
import com.iot.model.resultAndPage.PageResultBean;
import com.iot.service.SensorService;
import com.iot.service.SystemService;
import com.iot.service.UserService;
import com.iot.util.exception.DescribeException;
import com.iot.util.exception.ExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private UserService userService;

    @Override
    public List<Systems> queryUserSystemInformationByUserId(int userId) {
        return systemMapper.queryUserSystemInformationByUserId(userId);
    }

    @Cacheable(value = "SystemCache", key = "'system_'+#systemId")
    @Override
    public Object queryOneSystemInformation(Integer systemId) {
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
        if (systemParam.getDirection() == null || systemParam.getOperation() == null || systemParam.getName() == null) {
            throw new DescribeException(ExceptionEnum.SYSTEM_INFORMATION_INCOMPLETE);
        }
        systemMapper.insertSelective(systemParam);
        return systemMapper.queryUserSystemInformationByUserId(systemParam.getUserId());
    }

    @Caching(evict = {
            @CacheEvict(value = "SystemCache", key = "'system_'+#systemParam.getId()")
    })
    @Override
    public Object updateSystemInformation(SystemParam systemParam) {
        return systemMapper.updateByPrimaryKeySelective(systemParam);
    }

    @Caching(evict = {
            @CacheEvict(value = "SystemCache", key = "'system_'+#systemId"),
            @CacheEvict(value = "SystemCache", key = "'verifySysInUser_'+#userId+'_'+#systemId"),
    })
    @Override
    public Object deleteSystem(int userId, int systemId) {
        List<Sensor> sensor = sensorService.querySensorBySystemId(systemId);
        Iterator<Sensor> iterator = sensor.iterator();
        while (iterator.hasNext()) {
            Sensor it = iterator.next();
            sensorService.deleteSensor(it.getId());
        }
        return systemMapper.deleteByPrimaryKey(systemId);
    }

    @Cacheable(value = "SystemCache", key = "'verifySysInUser_'+#userId+'_'+#systemId", unless = "#result==null")
    @Override
    public Systems verifySystemInUser(int userId, int systemId) {
        Systems systems = systemMapper.verifySystemInUser(userId, systemId);
        return systems;
    }
}
