package com.iot.controller;

import com.iot.framework.aop.SystemControllerLog;
import com.iot.model.acceptParam.SystemParam;
import com.iot.model.resultAndPage.Result;
import com.iot.service.SystemService;
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

@Api(tags = {"中间层——系统"})
@RestController
@RequestMapping("/system")
public class SystemContrller {
    @Autowired
    private SystemService systemService;

    @Autowired
    private ExceptionHandle handle;

    @Autowired
    private MyVerificationUtil myVerificationUtil;

    @GetMapping("/getSys")
    @SystemControllerLog(logAction = "queryUserSystemInformationByUserId", logContent = "用户查看自己拥有的系统信息")
    @ApiOperation(value = "用户查看自己拥有的系统信息", notes = "用户查看自己拥有的系统信息")
    @ApiImplicitParams({
    })
    public Result queryUserSystemInformationByUserId(@CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int userId = (int) tokenData.get("id");
            result = ResultUtil.success(systemService.queryUserSystemInformationByUserId(userId));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @GetMapping("/queryAllSystemInformation_Admin")
    @SystemControllerLog(logAction = "queryAllSystemInformation_Admin", logContent = "查看所有系统信息（管理员）")
    @ApiOperation(value = "查看所有系统信息（管理员）", notes = "查看所有系统信息（管理员）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "此项可不填：填时则单独查看某用户名下系统信息", required = false, dataType = "int"),
            @ApiImplicitParam(name = "systemId", value = "此项可不填：填时则单独查看某系统信息", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pageNum", value = "第几页（不填默认为1)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小(不填默认为10)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "orderBy", value = "排序属性(不填默认“id”)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "正序或倒序[“ASC”或者“DESC”]（默认ASC）", required = false, dataType = "String"),
    })
    public Result queryAllSystemInformation_Admin(@RequestParam(value = "userId", defaultValue = "-1", required = false) Integer userId, @RequestParam(value = "systemId", defaultValue = "-1", required = false) Integer systemId, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam(value = "orderBy", defaultValue = "id") String orderBy, @RequestParam(value = "sort", defaultValue = "ASC") String sort, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            myVerificationUtil.adminVerification(tokenData);
            myVerificationUtil.sortVerification(sort);
            if (-1 != systemId) {
                result = ResultUtil.success(systemService.queryOneSystemInformation(systemId));
            } else if (-1 != userId) {
                result = ResultUtil.success(systemService.queryUserSystemInformationByUserId(userId));
            } else {
                result = ResultUtil.success(systemService.queryAllSystemInformation_Admin(pageNum, pageSize, orderBy, sort));
            }
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @PostMapping("/subSysData")
    @SystemControllerLog(logAction = "insertSystem", logContent = "新建系统")
    @ApiOperation(value = "新建系统", notes = "新建系统")
    @ApiImplicitParams({
    })
    public Result insertSystem(@RequestBody SystemParam systemParam, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int id = (int) tokenData.get("id");
            systemParam.setUserId(id);
            result = ResultUtil.success(systemService.insertSystem(systemParam));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @PutMapping("/editSys")
    @SystemControllerLog(logAction = "updateSystemInformation", logContent = "用户更新系统信息")
    @ApiOperation(value = "用户更新系统信息", notes = "用户更新系统信息")
    @ApiImplicitParams({
    })
    public Result updateSystemInformation(@RequestBody SystemParam systemParam, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int userId = (int) tokenData.get("id");
            myVerificationUtil.verifySystemInUser(userId, systemParam.getId());
            result = ResultUtil.success(systemService.updateSystemInformation(systemParam));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @DeleteMapping("/deleteSys")
    @SystemControllerLog(logAction = "deleteSystem", logContent = "用户删除系统")
    @ApiOperation(value = "用户删除系统", notes = "用户删除系统")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysId", value = "系统id", required = true, dataType = "int"),
    })
    public Result deleteSystem(@RequestParam int sysId, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int systemId = sysId;
            int userId = (int) tokenData.get("id");
            myVerificationUtil.verifySystemInUser(userId, systemId);
            result = ResultUtil.success(systemService.deleteSystem(userId, systemId));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }
}
