package com.iot.service.impl;

import com.iot.dao.GpsDataMapper;
import com.iot.framework.aop.SystemServiceLog;
import com.iot.model.GpsData;
import com.iot.model.User;
import com.iot.model.acceptParam.DataConditionParam;
import com.iot.service.GpsDataService;
import com.iot.util.myUtil.MyVerificationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GpsDataServiceImpl implements GpsDataService {
    @Autowired
    private MyVerificationUtil myVerificationUtil;

    @Autowired
    private GpsDataMapper gpsDataMapper;

    @Override
    public List<GpsData> queryGpsDataBySensorId(DataConditionParam dataConditionParam) {
        return gpsDataMapper.queryGpsDataBySensorId(dataConditionParam);
    }

    @Override
    @SystemServiceLog(logAction = "insertGpsDatas", logContent = "新增一条Gps数据")
    public Map insertGpsDatas(String apiKey, int sensorId, double lat, double lng) throws Exception{
        User user = myVerificationUtil.verifyApiKeyInUserLinkSensorId(apiKey, sensorId);
        Map result = new HashMap();
        result.put("user", user);

        GpsData gpsData=new GpsData();
        gpsData.setLat(lat);
        gpsData.setLng(lng);
        gpsData.setSensorId(sensorId);
        gpsDataMapper.insertSelective(gpsData);
        return result;
    }

    @Override
    public void deleteGpsDatas(int[] ids) {
        gpsDataMapper.deleteGpsDatas(ids);
        return;
    }
}
