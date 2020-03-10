package com.iot.controller;

import com.iot.framework.aop.SystemControllerLog;
import com.iot.model.acceptParam.DeviceParam;
import com.iot.model.resultAndPage.Result;
import com.iot.service.DeviceService;
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

@Api(tags = {"设备"})
@RestController
@RequestMapping("/device")
public class DeviceContrller {
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ExceptionHandle handle;

    @Autowired
    private MyVerificationUtil myVerificationUtil;

    @GetMapping("/getDev")
    @SystemControllerLog(logAction = "queryDeviceBySystemId", logContent = "用户查看某系统下的设备")
    @ApiOperation(value = "用户查看某系统下的设备", notes = "用户查看某系统下的设备")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysId", value = "系统id", required = true, dataType = "int"),
    })
    public Result queryDeviceBySystemId(@RequestParam Integer sysId, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        int userId = (int) tokenData.get("id");
        myVerificationUtil.verifySystemInUser(userId, sysId);
        try {
            result = ResultUtil.success(deviceService.queryDeviceBySystemId(sysId));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @GetMapping("/queryDeviceByUserId")
    @SystemControllerLog(logAction = "queryDeviceByUserId", logContent = "用户查看其名下的所有设备")
    @ApiOperation(value = "用户查看其名下的所有设备", notes = "用户查看其名下的所有设备")
    @ApiImplicitParams({
    })
    public Result queryDeviceByUserId(@CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        int userId = (int) tokenData.get("id");
        try {
            result = ResultUtil.success(deviceService.queryDeviceByUserId(userId));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @GetMapping("/queryAllDevice_Admin")
    @SystemControllerLog(logAction = "queryAllDevice_Admin", logContent = "管理员查看所有设备信息")
    @ApiOperation(value = "管理员查看所有设备信息", notes = "管理员查看所有设备信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "此项可不填：填时则单独查看某用户名下设备", required = false, dataType = "int"),
            @ApiImplicitParam(name = "systemId", value = "此项可不填：填时则单独查看某系统名下设备", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pageNum", value = "第几页（不填默认为1)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小(不填默认为10)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "orderBy", value = "排序属性(不填默认“id”)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "正序或倒序[“ASC”或者“DESC”]（默认ASC）", required = false, dataType = "String"),
    })
    public Result queryAllDevice_Admin(@RequestParam(value = "userId", defaultValue = "-1", required = false) Integer userId, @RequestParam(value = "systemId", defaultValue = "-1", required = false) Integer systemId, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam(value = "orderBy", defaultValue = "id") String orderBy, @RequestParam(value = "sort", defaultValue = "ASC") String sort, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        myVerificationUtil.adminVerification(tokenData);
        myVerificationUtil.sortVerification(sort);
        try {
            if (-1 != systemId) {
                result = ResultUtil.success(deviceService.queryDeviceBySystemId(systemId));
            } else if (-1 != userId) {
                result = ResultUtil.success(deviceService.queryDeviceByUserId(userId));
            } else {
                result = ResultUtil.success(deviceService.queryAllDevice_Admin(pageNum, pageSize, orderBy, sort));
            }
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @PostMapping("/subDevData")
    @SystemControllerLog(logAction = "insertDevice", logContent = "新建设备")
    @ApiOperation(value = "新建设备", notes = "新建设备")
    @ApiImplicitParams({
    })
    public Result insertDevice(@RequestBody DeviceParam deviceParam, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        int userId = (int) tokenData.get("id");
        myVerificationUtil.verifySystemInUser(userId, deviceParam.getSystemId());
        try {
            result = ResultUtil.success(deviceService.insertDevice(deviceParam));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @PutMapping("/updateDevice")
    @SystemControllerLog(logAction = "updateDevice", logContent = "用户更新设备信息")
    @ApiOperation(value = "用户更新设备信息", notes = "用户更新设备信息")
    @ApiImplicitParams({
    })
    public Result updateDevice(@RequestBody DeviceParam deviceParam, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        int userId = (int) tokenData.get("id");
        myVerificationUtil.verifyDeviceInUser(userId, deviceParam.getId());
        try {
            result = ResultUtil.success(deviceService.updateDevice(deviceParam));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @DeleteMapping("/deleteDevice")
    @SystemControllerLog(logAction = "deleteDevice", logContent = "用户删除设备")
    @ApiOperation(value = "用户删除设备", notes = "用户删除设备")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "/deleteDevice", value = "设备id", required = true, dataType = "int"),
    })
    public Result deleteDevice(@RequestParam int devId, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        int userId = (int) tokenData.get("id");
        myVerificationUtil.verifyDeviceInUser(userId, devId);
        try {
            result = ResultUtil.success(deviceService.deleteDevice(devId));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }
}
