package com.iot.controller;

import com.iot.framework.aop.SystemControllerLog;
import com.iot.model.acceptParam.DataConditionParam;
import com.iot.model.resultAndPage.Result;
import com.iot.service.ImgDataService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Api(tags = {"数据点——图像数据"})
@RestController
@RequestMapping("/imgData")
public class ImgDataController {
    @Autowired
    private ImgDataService imgDataService;

    @Autowired
    private ExceptionHandle handle;

    @Autowired
    private MyVerificationUtil myVerificationUtil;

    @PostMapping("/queryImgBySensorIdAndTime")
    @SystemControllerLog(logAction = "queryImgBySensorIdAndTime", logContent = "用户查看图像传感器下的图像数据")
    @ApiOperation(value = "用户查看图像传感器下的图像数据", notes = "用户查看图像传感器下的图像数据")
    @ApiImplicitParams({
    })
    public Result queryImgBySensorIdAndTime(@RequestBody DataConditionParam dataConditionParam, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int userId = (int) tokenData.get("id");
            myVerificationUtil.verifySensorInUser(userId, dataConditionParam.getSensorId());
            result = ResultUtil.success(imgDataService.queryImgBySensorIdAndTime(dataConditionParam));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @ApiKeyPassToken
    @PostMapping("/uploadImg")
    @ApiOperation(value = "新增一条图像数据(10M以内)", notes = "新增一条图像数据(10M以内)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiKey", value = "apiKey", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "sensorId", value = "传感器id", required = true, dataType = "String"),
    })
    public Result uploadImg(@RequestHeader String apiKey, @RequestParam int sensorId, @RequestParam("file") MultipartFile file) throws Exception {
        Result result = ResultUtil.success();
        try {
            myVerificationUtil.verifySensorType(sensorId, "图像数据");
            imgDataService.uploadImg(apiKey, sensorId, file);
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @DeleteMapping("/deleteImgs")
    @SystemControllerLog(logAction = "deleteImgs", logContent = "批量删除图像传感器下图像数据点")
    @ApiOperation(value = "批量删除图像传感器下图像数据点", notes = "批量删除图像传感器下图像数据点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids[]", value = "需要删除的id数组", required = true, allowMultiple = true, dataType = "int"),
            @ApiImplicitParam(name = "sensorId", value = "传感器id", required = true, dataType = "String"),
    })
    public Result deleteImgs(@RequestParam(value = "ids[]") int[] ids, @RequestParam int sensorId, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int userId = (int) tokenData.get("id");
            myVerificationUtil.verifySensorInUser(userId, sensorId);
            imgDataService.deleteImgs(ids);
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }
}
