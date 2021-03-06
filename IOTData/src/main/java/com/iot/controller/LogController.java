package com.iot.controller;

import com.iot.framework.aop.SystemControllerLog;
import com.iot.model.resultAndPage.Result;
import com.iot.service.LogService;
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

@Api(tags = {"#####日志"})
@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LogService logService;

    @Autowired
    private ExceptionHandle handle;

    @Autowired
    private MyVerificationUtil myVerificationUtil;

    @GetMapping("/queryAllLog_Admin")
    @SystemControllerLog(logAction = "queryAllLog_Admin", logContent = "查看所有日志操作（管理员）")
    @ApiOperation(value = "查看所有日志操作（管理员）", notes = "查看所有日志操作（管理员）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "此项可不填：不填时查看所有日志记录，填时则单独查看某用户日志记录", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pageNum", value = "第几页（不填默认为1)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小(不填默认为10)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "orderBy", value = "排序属性(不填默认“id”)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "正序或倒序[“ASC”或者“DESC”]（默认ASC）", required = false, dataType = "String"),
    })
    public Result queryAllLog_Admin(@RequestParam(value = "userId", defaultValue = "-1", required = false) Integer userId, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam(value = "orderBy", defaultValue = "id") String orderBy, @RequestParam(value = "sort", defaultValue = "ASC") String sort, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            myVerificationUtil.adminVerification(tokenData);
            myVerificationUtil.sortVerification(sort);
            if (-1 == userId) {
                result = ResultUtil.success(logService.queryAllLog_Admin(pageNum, pageSize, orderBy, sort));
            } else {
                result = ResultUtil.success(logService.queryOneUserLog(userId, pageNum, pageSize, orderBy, sort));
            }
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @GetMapping("/queryOneUserLog")
    @SystemControllerLog(logAction = "queryOneUserLog", logContent = "用户查看自己的日志")
    @ApiOperation(value = "用户查看自己的日志", notes = "用户查看自己的日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页（不填默认为1)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小(不填默认为10)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "orderBy", value = "排序属性(不填默认“id”)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "正序或倒序[“ASC”或者“DESC”]（默认ASC）", required = false, dataType = "String"),
    })
    public Result queryOneUserLog(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam(value = "orderBy", defaultValue = "id") String orderBy, @RequestParam(value = "sort", defaultValue = "ASC") String sort, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            myVerificationUtil.sortVerification(sort);
            int userId = (int) tokenData.get("id");
            result = ResultUtil.success(logService.queryOneUserLog(userId, pageNum, pageSize, orderBy, sort));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @DeleteMapping("/deleteOneUserLog")
    @SystemControllerLog(logAction = "deleteOneUserLog", logContent = "用户删除自己的日志操作")
    @ApiOperation(value = "用户删除自己的日志操作", notes = "用户删除自己的日志操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids[]", value = "需要删除的id数组", required = true, allowMultiple = true, dataType = "int"),
    })
    public Result deleteOneUserLog(@RequestParam(value = "ids[]") int[] ids, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        int userId = (int) tokenData.get("id");
        try {
            result = ResultUtil.success(logService.deleteOneUserLog(ids, userId));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @DeleteMapping("/deleteLog_Admin")
    @SystemControllerLog(logAction = "deleteLog_Admin", logContent = "批量删除所有日志记录（管理员）")
    @ApiOperation(value = "批量删除所有日志记录（管理员）", notes = "批量删除所有日志记录（管理员）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids[]", value = "需要删除的id数组", required = true, allowMultiple = true, dataType = "int"),
    })
    public Result deleteLog_Admin(@RequestParam(value = "ids[]") int[] ids, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        myVerificationUtil.adminVerification(tokenData);
        try {
            result = ResultUtil.success(logService.deleteLog_Admin(ids));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }
}
