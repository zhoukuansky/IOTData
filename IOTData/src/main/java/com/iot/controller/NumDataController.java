package com.iot.controller;

import com.iot.framework.aop.SystemControllerLog;
import com.iot.model.acceptParam.DataConditionParam;
import com.iot.model.resultAndPage.Result;
import com.iot.service.NumDataService;
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

@Api(tags = {"数据点——数值数据点"})
@RestController
@RequestMapping("/numData")
public class NumDataController {
    @Autowired
    private NumDataService numDataService;

    @Autowired
    private ExceptionHandle handle;

    @Autowired
    private MyVerificationUtil myVerificationUtil;

    @PostMapping("/queryNumDataBySensorId")
    @SystemControllerLog(logAction = "queryNumDataBySensorId", logContent = "用户查看数值传感器下的数值数据")
    @ApiOperation(value = "用户查看数值传感器下的数值数据", notes = "用户查看数值传感器下的数值数据")
    @ApiImplicitParams({
    })
    public Result queryNumDataBySensorId(@RequestBody DataConditionParam dataConditionParam, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int userId = (int) tokenData.get("id");
            myVerificationUtil.verifySensorInUser(userId, dataConditionParam.getSensorId());

            result = ResultUtil.success(numDataService.queryNumDataBySensorId(dataConditionParam));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @ApiKeyPassToken
    @PostMapping("/insertNumDatas")
    @ApiOperation(value = "新增多条数值数据，数组传值", notes = "新增多条数值数据，数组传值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiKey", value = "apiKey", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "sensorId", value = "传感器id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "values[]", value = "数据值数组", required = true, allowMultiple = true, dataType = "double")
    })
    public Result insertNumDatas(@RequestHeader String apiKey, @RequestParam int sensorId, @RequestParam(value = "values[]") double[] values) throws Exception {
        Result result = ResultUtil.success();
        try {
            myVerificationUtil.verifySensorType(sensorId, "数值数据");
            numDataService.insertNumDatas(apiKey, sensorId, values);
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @DeleteMapping("/deleteNumDatas")
    @SystemControllerLog(logAction = "deleteNumDatas", logContent = "批量删除数值传感器下数值数据点")
    @ApiOperation(value = "批量删除数值传感器下数值数据点", notes = "批量删除数值传感器下数值数据点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids[]", value = "需要删除的id数组", required = true, allowMultiple = true, dataType = "int"),
            @ApiImplicitParam(name = "sensorId", value = "传感器id", required = true, dataType = "String"),
    })
    public Result deleteNumDatas(@RequestParam(value = "ids[]") int[] ids, @RequestParam int sensorId, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int userId = (int) tokenData.get("id");
            myVerificationUtil.verifySensorInUser(userId, sensorId);
            numDataService.deleteNumDatas(ids);
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }
}
