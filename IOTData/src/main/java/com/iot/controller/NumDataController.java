package com.iot.controller;

import com.iot.util.exception.ExceptionHandle;
import com.iot.util.myUtil.MyVerificationUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"数值数据"})
@RestController
@RequestMapping("/numData")
public class NumDataController {
    @Autowired
    private NumDataService numDataService;

    @Autowired
    private ExceptionHandle handle;

    @Autowired
    private MyVerificationUtil myVerificationUtil;

    @GetMapping("/queryNumDataBySensorId")
    @SystemControllerLog(logAction = "queryNumDataBySensorId", logContent = "用户查看某传感器下的数值数据")
    @ApiOperation(value = "用户查看某传感器下的数值数据", notes = "用户查看某传感器下的数值数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sensorId", value = "传感器id", required = true, dataType = "int"),
    })
    public Result querySensorBySystemId(@RequestParam("sensorId") Integer sensorId, @CurrentUser Map tokenData) throws Exception {

    }
}
