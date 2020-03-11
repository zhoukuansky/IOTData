package com.iot.service.impl;

import com.iot.dao.NumDataMapper;
import com.iot.framework.aop.SystemServiceLog;
import com.iot.model.NumData;
import com.iot.model.User;
import com.iot.model.acceptParam.DataConditionParam;
import com.iot.service.NumDataService;
import com.iot.util.myUtil.MyVerificationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NumDataServiceImpl implements NumDataService {
    @Autowired
    private MyVerificationUtil myVerificationUtil;

    @Autowired
    private NumDataMapper numDataMapper;

    @Override
    public List<NumData> queryNumDataBySensorId(DataConditionParam dataConditionParam) {
        return numDataMapper.queryNumDataBySensorId(dataConditionParam);
    }

    @Override
    @SystemServiceLog(logAction = "insertNumDatas", logContent = "新增多条数值数据(数组传值)")
    public Map insertNumDatas(String apiKey, int sensorId, double[] values) throws Exception {
        User user = myVerificationUtil.verifyApiKeyInUserLinkSensorId(apiKey, sensorId);
        Map result = new HashMap();
        result.put("user", user);

        numDataMapper.insertNumDatas(sensorId, values);
        return result;
    }

    @Override
    public void deleteNumDatas(int[] ids) {
        numDataMapper.deleteNumDatas(ids);
        return;
    }
}
