package com.iot.controller;

import com.iot.framework.aop.SystemControllerLog;
import com.iot.model.acceptParam.SensorParam;
import com.iot.model.resultAndPage.Result;
import com.iot.service.SensorService;
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

@Api(tags = {"中间层——传感器"})
@RestController
@RequestMapping("/senser")
public class SersorController {
    @Autowired
    private SensorService sensorService;

    @Autowired
    private ExceptionHandle handle;

    @Autowired
    private MyVerificationUtil myVerificationUtil;

    @GetMapping("/getSensor")
    @SystemControllerLog(logAction = "querySensorBySystemId", logContent = "用户查看某系统下的传感器信息")
    @ApiOperation(value = "用户查看某系统下的传感器信息", notes = "用户查看某系统下的传感器信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysId", value = "系统id", required = true, dataType = "int"),
    })
    public Result querySensorBySystemId(@RequestParam Integer sysId, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int userId = (int) tokenData.get("id");
            myVerificationUtil.verifySystemInUser(userId, sysId);
            result = ResultUtil.success(sensorService.querySensorBySystemId(sysId));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @GetMapping("/querySensorByUserId")
    @SystemControllerLog(logAction = "querySensorByUserId", logContent = "用户查看其名下的所有传感器")
    @ApiOperation(value = "用户查看其名下的所有传感器", notes = "用户查看其名下的所有传感器")
    @ApiImplicitParams({
    })
    public Result querySensorByUserId(@CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int userId = (int) tokenData.get("id");
            result = ResultUtil.success(sensorService.querySensorByUserId(userId));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @GetMapping("/queryAllSensor_Admin")
    @SystemControllerLog(logAction = "queryAllSensor_Admin", logContent = "管理员查看所有传感器信息")
    @ApiOperation(value = "管理员查看所有传感器信息", notes = "管理员查看所有传感器信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "此项可不填：填时则单独查看某用户名下传感器信息", required = false, dataType = "int"),
            @ApiImplicitParam(name = "systemId", value = "此项可不填：填时则单独查看某系统下传感器信息", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pageNum", value = "第几页（不填默认为1)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小(不填默认为10)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "orderBy", value = "排序属性(不填默认“id”)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "正序或倒序[“ASC”或者“DESC”]（默认ASC）", required = false, dataType = "String"),
    })
    public Result queryAllSensor_Admin(@RequestParam(value = "userId", defaultValue = "-1", required = false) Integer userId, @RequestParam(value = "systemId", defaultValue = "-1", required = false) Integer systemId, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam(value = "orderBy", defaultValue = "id") String orderBy, @RequestParam(value = "sort", defaultValue = "ASC") String sort, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            myVerificationUtil.adminVerification(tokenData);
            myVerificationUtil.sortVerification(sort);
            if (-1 != systemId) {
                result = ResultUtil.success(sensorService.querySensorBySystemId(systemId));
            } else if (-1 != userId) {
                result = ResultUtil.success(sensorService.querySensorByUserId(userId));
            } else {
                result = ResultUtil.success(sensorService.queryAllSensor_Admin(pageNum, pageSize, orderBy, sort));
            }
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @PostMapping("/addSensor")
    @SystemControllerLog(logAction = "insertSensor", logContent = "新建传感器")
    @ApiOperation(value = "新建传感器", notes = "新建传感器")
    @ApiImplicitParams({
    })
    public Result insertSensor(@RequestBody SensorParam sensorParam, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int userId = (int) tokenData.get("id");
            myVerificationUtil.verifySystemInUser(userId, sensorParam.getSystemId());
            result = ResultUtil.success(sensorService.insertSensor(sensorParam));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @PutMapping("/editSensor")
    @SystemControllerLog(logAction = "updateSensor", logContent = "用户更新传感器信息")
    @ApiOperation(value = "用户更新传感器信息", notes = "用户更新传感器信息")
    @ApiImplicitParams({
    })
    public Result updateSensor(@RequestBody SensorParam sensorParam, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int userId = (int) tokenData.get("id");
            myVerificationUtil.verifySensorInUser(userId, sensorParam.getId());
            result = ResultUtil.success(sensorService.updateSensor(sensorParam));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @DeleteMapping("/deleteSensor")
    @SystemControllerLog(logAction = "deleteSensor", logContent = "用户删除传感器")
    @ApiOperation(value = "用户删除传感器", notes = "用户删除传感器")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sensorId", value = "传感器id", required = true, dataType = "int"),
    })
    public Result deleteSensor(@RequestParam int sensorId, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int userId = (int) tokenData.get("id");
            myVerificationUtil.verifySensorInUser(userId, sensorId);
            result = ResultUtil.success(sensorService.deleteSensor(sensorId));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }
}
