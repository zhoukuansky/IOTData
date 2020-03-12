package com.iot.service.impl;

import com.github.pagehelper.PageHelper;
import com.iot.dao.SensorMapper;
import com.iot.model.Sensor;
import com.iot.model.Systems;
import com.iot.model.acceptParam.SensorParam;
import com.iot.model.resultAndPage.PageResultBean;
import com.iot.service.SensorService;
import com.iot.service.SystemService;
import com.iot.util.exception.DescribeException;
import com.iot.util.exception.ExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class SensorServiceImpl implements SensorService {
    @Autowired
    private SensorMapper sensorMapper;

    @Autowired
    private SystemService systemService;

    @Override
    public List<Sensor> querySensorBySystemId(Integer systemId) {
        return sensorMapper.querySensorBySystemId(systemId);
    }

    @Override
    public Object querySensorByUserId(int userId) {
        List<Sensor> result = new ArrayList<Sensor>();
        List<Systems> systems = systemService.queryUserSystemInformationByUserId(userId);
        Iterator<Systems> it = systems.iterator();
        while (it.hasNext()) {
            Systems s = (Systems) it.next();
            result.addAll(sensorMapper.querySensorBySystemId(s.getId()));
        }
        return result;
    }

    @Override
    public Object queryAllSensor_Admin(Integer pageNum, Integer pageSize, String orderBy, String sort) {
        String order = orderBy + " " + sort;
        PageHelper.startPage(pageNum, pageSize, order);
        return new PageResultBean<Sensor>(sensorMapper.queryAllSensor_Admin());
    }

    @Override
    public synchronized Object insertSensor(SensorParam sensorParam) {
        sensorParam.setStatus(0);
        if (sensorParam.getName() == null || sensorParam.getDataType() == null) {
            throw new DescribeException(ExceptionEnum.SENSOR_INFORMATION_INCOMPLETE);
        }
        sensorMapper.insertSelective(sensorParam);
        return sensorMapper.querySensorBySystemId(sensorParam.getSystemId());
    }

    @Override
    public Object updateSensor(SensorParam sensorParam) {
        return sensorMapper.updateByPrimaryKeySelective(sensorParam);
    }


    @CacheEvict(value = "SensorCache", key = "'verifySenInUser_'+#sensorId")
    @Override
    public Object deleteSensor(int sensorId) {
        return sensorMapper.deleteByPrimaryKey(sensorId);
    }

    @Cacheable(value = "SensorCache", key = "'verifySenInUser_'+#sensorId", unless = "#result==null")
    @Override
    public Sensor querySensorBySensorId(int sensorId) {
        return sensorMapper.selectByPrimaryKey(sensorId);
    }
}
