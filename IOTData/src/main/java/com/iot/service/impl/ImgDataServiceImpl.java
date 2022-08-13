package com.iot.service.impl;

import com.iot.dao.ImgDataMapper;
import com.iot.framework.aop.SystemServiceLog;
import com.iot.model.ImgData;
import com.iot.model.User;
import com.iot.model.acceptParam.DataConditionParam;
import com.iot.service.ImgDataService;
import com.iot.service.SensorService;
import com.iot.util.myUtil.MyImageUtil;
import com.iot.util.myUtil.MyVerificationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImgDataServiceImpl implements ImgDataService {

    @Autowired
    private MyVerificationUtil myVerificationUtil;

    @Autowired
    private MyImageUtil myImageUtil;

    @Autowired
    private ImgDataMapper imgDataMapper;

    @Autowired
    private SensorService sensorService;

    @Override
    public List<ImgData> queryImgBySensorId(int sensorId) {
        return imgDataMapper.queryImgBySensorId(sensorId);
    }

    @Override
    public List<ImgData> queryImgBySensorIdAndTime(DataConditionParam dataConditionParam) {
        return imgDataMapper.queryImgBySensorIdAndTime(dataConditionParam);
    }

    @Override
    @SystemServiceLog(logAction = "uploadImg", logContent = "新增一条图像数据")
    public synchronized Map uploadImg(String apiKey, int sensorId, MultipartFile file) throws Exception {
        User user = myVerificationUtil.verifyApiKeyInUserLinkSensorId(apiKey, sensorId);
        Map<String, User> result = new HashMap<String, User>();
        result.put("user", user);

        String img = myImageUtil.saveImage(sensorId, file);

        ImgData imgData = new ImgData();
        imgData.setSensorId(sensorId);
        imgData.setImg(img);
        imgDataMapper.insertSelective(imgData);

        return result;
    }

    @Override
    public void deleteImgs(int[] ids) {
        List<ImgData> imgs = imgDataMapper.selectByPrimaryKey(ids);

        myImageUtil.deleteImage(imgs);

        imgDataMapper.deleteImgs(ids);
        return;
    }

}
