package com.iot.service;

import com.iot.model.ImgData;
import com.iot.model.acceptParam.DataConditionParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ImgDataService {
    List<ImgData> queryImgBySensorId(int sensorId);

    List<ImgData> queryImgBySensorIdAndTime(DataConditionParam dataConditionParam);

    Map uploadImg(String apiKey, int sensorId, MultipartFile file) throws Exception;

    void deleteImgs(int[] ids);
}
