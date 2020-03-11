package com.iot.service;

import com.iot.model.NumData;
import com.iot.model.acceptParam.DataConditionParam;

import java.util.List;
import java.util.Map;

public interface NumDataService {
    List<NumData> queryNumDataBySensorId(DataConditionParam dataConditionParam);

    Map insertNumDatas(String apiKey, int sensorId, double[] values) throws Exception;

    void deleteNumDatas(int[] ids);
}
