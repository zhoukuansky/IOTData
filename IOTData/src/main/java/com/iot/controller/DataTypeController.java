package com.iot.controller;

import com.iot.framework.aop.SystemControllerLog;
import com.iot.model.resultAndPage.Result;
import com.iot.service.AdminService;
import com.iot.util.authentication.CurrentUser;
import com.iot.util.authentication.VerificationUtil;
import com.iot.util.exception.ExceptionHandle;
import com.iot.util.exception.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = {"管理员设置可选数据类型"})
@RestController
@RequestMapping("/dataTypeSetting")
public class DataTypeController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private ExceptionHandle handle;

    @GetMapping("/queryAllDataType_Admin")
    @SystemControllerLog(logAction = "adminQueryAllLog", logContent = "管理员查看所有数据类型")
    @ApiOperation(value = "管理员查看所有数据类型", notes = "管理员查看所有数据类型")
    @ApiImplicitParams({
    })
    public Result adminQueryAllDataType(@CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        VerificationUtil.adminVerification(tokenData);
        try {
                result = ResultUtil.success(adminService.adminQueryAllDataType());
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @PostMapping("/insertDataType_Admin")
    @ApiOperation(value = "新建传感器数据类型", notes = "新建传感器数据类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "word", value = "传感器数据类型（描述）", required = true, dataType = "String"),
    })
    public Result insertUser(@RequestParam("word") String word,@CurrentUser Map tokenData) throws Exception{
        Result result = ResultUtil.success();
        VerificationUtil.adminVerification(tokenData);
        try {
            result = ResultUtil.success(adminService.adminInsertDataType(word));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }
}
