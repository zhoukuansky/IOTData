package com.iot.controller;

import com.iot.framework.aop.SystemControllerLog;
import com.iot.model.acceptParam.IdsOfDelete;
import com.iot.model.resultAndPage.Result;
import com.iot.service.AdminService;
import com.iot.util.authentication.CurrentUser;
import com.iot.util.exception.ExceptionHandle;
import com.iot.util.exception.ResultUtil;
import com.iot.util.myUtil.VerificationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = {"设置操作系统选择项（管理员）"})
@RestController
@RequestMapping("/directionDescribeSetting")
public class OperationController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private ExceptionHandle handle;

    @GetMapping("/queryAllOperationType_Admin")
    @SystemControllerLog(logAction = "queryAllOperationType_Admin", logContent = "查看所有的操作系统类型（管理员）")
    @ApiOperation(value = "查看所有的操作系统类型（管理员）", notes = "查看所有的操作系统类型（管理员）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页（不填默认为1)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小(不填默认为10)", required = false, dataType = "String"),
    })
    public Result queryAllOperationType_Admin(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        VerificationUtil.adminVerification(tokenData);
        try {
            result = ResultUtil.success(adminService.queryAllOperationType_Admin(pageNum,pageSize));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @PostMapping("/insertOperationType_Admin")
    @SystemControllerLog(logAction = "insertOperationType_Admin", logContent = "新建操作系统选择项（管理员）")
    @ApiOperation(value = "新建操作系统选择项（管理员）", notes = "新建操作系统选择项（管理员）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "word", value = "新建系统方向选择项类型（描述）", required = true, dataType = "String"),
    })
    public Result insertOperationType_Admin(@RequestParam("word") String word, @CurrentUser Map tokenData) throws Exception{
        Result result = ResultUtil.success();
        VerificationUtil.adminVerification(tokenData);
        try {
            result = ResultUtil.success(adminService.insertOperationType_Admin(word));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @DeleteMapping("/deleteOperationType_Admin")
    @SystemControllerLog(logAction = "deleteOperationType_Admin", logContent = "批量删除操作系统选择项（管理员）")
    @ApiOperation(value = "批量删除操作系统选择项（管理员）", notes = "批量删除操作系统选择项（管理员）")
    @ApiImplicitParams({
    })
    public Result deleteOperationType_Admin(@RequestBody IdsOfDelete idsOfDelete, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        VerificationUtil.adminVerification(tokenData);
        int id = (int) tokenData.get("id");
        try {
            result = ResultUtil.success(adminService.deleteOperationType_Admin(idsOfDelete.getIds()));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }
}
