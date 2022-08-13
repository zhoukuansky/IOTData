package com.iot.service;

import com.iot.model.GpsData;
import com.iot.model.acceptParam.DataConditionParam;

import java.util.List;
import java.util.Map;

/**
 * @author HP
 */
public interface GpsDataService {
    List<GpsData> queryGpsDataBySensorId(DataConditionParam dataConditionParam);

    Map insertGpsDatas(String apiKey, int sensorId, double lat, double lng) throws Exception;

    void deleteGpsDatas(int[] ids);
}
