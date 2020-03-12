package com.iot.controller;

import com.iot.framework.aop.SystemControllerLog;
import com.iot.model.acceptParam.DataConditionParam;
import com.iot.model.resultAndPage.Result;
import com.iot.service.GpsDataService;
import com.iot.util.authentication.ApiKeyPassToken;
import com.iot.util.authentication.CurrentUser;
import com.iot.util.exception.ExceptionHandle;
import com.iot.util.exception.ResultUtil;
import com.iot.util.myUtil.MyVerificationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = {"数据点——经纬度数据"})
@RestController
@RequestMapping("/gpsData")
public class GpsDataController {
    @Autowired
    private GpsDataService gpsDataService;

    @Autowired
    private ExceptionHandle handle;

    @Autowired
    private MyVerificationUtil myVerificationUtil;

    @PostMapping("/queryGpsDataBySensorId")
    @SystemControllerLog(logAction = "queryGpsDataBySensorId", logContent = "用户查看经纬度传感器下的经纬度数据")
    @ApiOperation(value = "用户查看经纬度传感器下的经纬度数据", notes = "用户查看经纬度传感器下的经纬度数据")
    @ApiImplicitParams({
    })
    public Result queryGpsDataBySensorId(@RequestBody DataConditionParam dataConditionParam, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int userId = (int) tokenData.get("id");
            myVerificationUtil.verifySensorInUser(userId, dataConditionParam.getSensorId());

            result = ResultUtil.success(gpsDataService.queryGpsDataBySensorId(dataConditionParam));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @ApiKeyPassToken
    @PostMapping("/insertGpsDatas")
    @ApiOperation(value = "新增一条经纬度数据", notes = "新增一条经纬度数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiKey", value = "apiKey", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "sensorId", value = "传感器id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "lat", value = "纬度", required = true, dataType = "double"),
            @ApiImplicitParam(name = "lng", value = "经度", required = true, dataType = "double"),
    })
    public Result insertGpsDatas(@RequestHeader String apiKey, @RequestParam int sensorId, @RequestParam double lat, @RequestParam double lng) throws Exception {
        Result result = ResultUtil.success();
        try {
            myVerificationUtil.verifySensorType(sensorId, "经纬度数据");
            gpsDataService.insertGpsDatas(apiKey, sensorId, lat, lng);
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @DeleteMapping("/deleteGpsDatas")
    @SystemControllerLog(logAction = "deleteGpsDatas", logContent = "批量删除经纬度传感器下经纬度数据点")
    @ApiOperation(value = "批量删除经纬度传感器下经纬度数据点", notes = "批量删除经纬度传感器下经纬度数据点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids[]", value = "需要删除的id数组", required = true, allowMultiple = true, dataType = "int"),
            @ApiImplicitParam(name = "sensorId", value = "传感器id", required = true, dataType = "String"),
    })
    public Result deleteGpsDatas(@RequestParam(value = "ids[]") int[] ids, @RequestParam int sensorId, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int userId = (int) tokenData.get("id");
            myVerificationUtil.verifySensorInUser(userId, sensorId);
            gpsDataService.deleteGpsDatas(ids);
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }
}
