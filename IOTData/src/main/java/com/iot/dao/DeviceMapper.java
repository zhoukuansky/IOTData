package com.iot.dao;

import com.iot.model.Device;
import com.iot.model.acceptParam.DeviceParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Device record);

    int insertSelective(DeviceParam deviceParam);

    Device selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceParam deviceParam);

    int updateByPrimaryKey(Device record);

    List<Device> queryDeviceBySystemId(Integer systemId);

    List<Device> queryAllDevice_Admin();
}