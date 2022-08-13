package com.iot.service.impl;

import com.github.pagehelper.PageHelper;
import com.iot.dao.DeviceMapper;
import com.iot.model.Device;
import com.iot.model.Systems;
import com.iot.model.acceptParam.DeviceParam;
import com.iot.model.resultAndPage.PageResultBean;
import com.iot.service.DeviceService;
import com.iot.service.SystemService;
import com.iot.util.exception.DescribeException;
import com.iot.util.exception.ExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private SystemService systemService;

    @Override
    public List<Device> queryDeviceBySystemId(Integer systemId) {
        return deviceMapper.queryDeviceBySystemId(systemId);
    }

    @Override
    public Object queryDeviceByUserId(int userId) {
        List<Device> result = new ArrayList<Device>();
        List<Systems> systems = systemService.queryUserSystemInformationByUserId(userId);
        Iterator<Systems> it = systems.iterator();
        while (it.hasNext()) {
            Systems s = (Systems) it.next();
            result.addAll(deviceMapper.queryDeviceBySystemId(s.getId()));
        }
        return result;
    }

    @Override
    public Object queryAllDevice_Admin(Integer pageNum, Integer pageSize, String orderBy, String sort) {
        String order = orderBy + " " + sort;
        PageHelper.startPage(pageNum, pageSize, order);
        return new PageResultBean<Device>(deviceMapper.queryAllDevice_Admin());
    }

    @Override
    public synchronized Object insertDevice(DeviceParam deviceParam) {
        deviceParam.setStatus(0);
        if (deviceParam.getName() == null) {
            throw new DescribeException(ExceptionEnum.DEVICE_INFORMATION_INCOMPLETE);
        }
        deviceMapper.insertSelective(deviceParam);
        return deviceMapper.queryDeviceBySystemId(deviceParam.getSystemId());
    }

    @Override
    public Object updateDevice(DeviceParam deviceParam) {
        return deviceMapper.updateByPrimaryKeySelective(deviceParam);
    }

    @Caching(evict = {
            @CacheEvict(value = "DeviceCache", key = "'verifyDevInUser_'+#deviceId"),
    })
    @Override
    public Object deleteDevice(int deviceId) {
        return deviceMapper.deleteByPrimaryKey(deviceId);
    }

    @Cacheable(value = "DeviceCache", key = "'verifyDevInUser_'+#deviceId", unless = "#result==null")
    @Override
    public Device verifyDeviceInUser(int deviceId) {
        return deviceMapper.selectByPrimaryKey(deviceId);
    }
}
