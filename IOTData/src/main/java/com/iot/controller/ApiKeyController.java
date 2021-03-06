package com.iot.controller;

import com.iot.framework.aop.SystemControllerLog;
import com.iot.model.resultAndPage.Result;
import com.iot.service.UserService;
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

@Api(tags = {"用户——关于ApiKey"})
@RestController
@RequestMapping("/apiKey")
public class ApiKeyController {
    @Autowired
    private UserService userService;

    @Autowired
    private ExceptionHandle handle;

    @Autowired
    private MyVerificationUtil myVerificationUtil;

    @GetMapping("/queryApiKey")
    @SystemControllerLog(logAction = "queryApiKey", logContent = "用户查询自己的ApiKey")
    @ApiOperation(value = "用户查询自己的ApiKey", notes = "用户查询自己的ApiKey")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "验证密码", required = true, dataType = "String"),
    })
    public Result queryApiKey(@RequestParam String password, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int userId = (int) tokenData.get("id");
            myVerificationUtil.verifyPassword(password, userId);
            result = ResultUtil.success(userService.queryApiKey(userId));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @PutMapping("/updateApiKey")
    @SystemControllerLog(logAction = "updateApiKey", logContent = "用户更新自己的ApiKey")
    @ApiOperation(value = "用户更新自己的ApiKey", notes = "用户更新自己的ApiKey")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "验证密码", required = true, dataType = "String"),
    })
    public Result updateApiKey(@RequestParam String password, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int userId = (int) tokenData.get("id");
            myVerificationUtil.verifyPassword(password, userId);
            result = ResultUtil.success(userService.updateApiKey(userId));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }
}
