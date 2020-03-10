package com.iot.controller;

import com.iot.framework.aop.SystemControllerLog;
import com.iot.model.resultAndPage.Result;
import com.iot.service.AdminService;
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

@Api(tags = {"设置传感器数据类型"})
@RestController
@RequestMapping("/dataTypeSetting")
public class DataTypeController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private ExceptionHandle handle;

    @Autowired
    private MyVerificationUtil myVerificationUtil;

    @GetMapping("/queryAllDataType")
    @SystemControllerLog(logAction = "queryAllDataType", logContent = "查看所有数据类型")
    @ApiOperation(value = "查看所有数据类型", notes = "查看所有数据类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页（不填默认为1)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小(不填默认为10)", required = false, dataType = "String"),
    })
    public Result queryAllDataType(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            result = ResultUtil.success(adminService.queryAllDataType(pageNum, pageSize));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @PostMapping("/insertDataType_Admin")
    @SystemControllerLog(logAction = "insertDataType_Admin", logContent = "新建传感器数据类型（管理员）")
    @ApiOperation(value = "新建传感器数据类型（管理员）", notes = "新建传感器数据类型（管理员）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "word", value = "传感器数据类型（描述）", required = true, dataType = "String"),
    })
    public Result insertDataType_Admin(@RequestParam String word, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        myVerificationUtil.adminVerification(tokenData);
        try {
            result = ResultUtil.success(adminService.insertDataType_Admin(word));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @DeleteMapping("/deleteDataType_Admin")
    @SystemControllerLog(logAction = "deleteDataType_Admin", logContent = "批量删除传感器数据类型（管理员）")
    @ApiOperation(value = "批量删除传感器数据类型（管理员）", notes = "批量删除传感器数据类型（管理员）")
    @ApiImplicitParams({
            @ApiImplicitParam(name="ids[]", value="需要删除的id数组", required=true,allowMultiple=true, dataType = "int"),
    })
    public Result deleteDataType_Admin(@RequestParam(value = "ids[]") int[] ids, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        myVerificationUtil.adminVerification(tokenData);
        int id = (int) tokenData.get("id");
        try {
            result = ResultUtil.success(adminService.deleteDataType_Admin(ids));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }
}
