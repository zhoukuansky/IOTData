package com.iot.controller;

import com.iot.util.exception.ExceptionHandle;
import com.iot.util.myUtil.MyVerificationUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"经纬度数据"})
@RestController
@RequestMapping("/gpsData")
public class GpsDataController {
//    @Autowired
//    private GpsDataService gpsDataService;

    @Autowired
    private ExceptionHandle handle;

    @Autowired
    private MyVerificationUtil myVerificationUtil;

}