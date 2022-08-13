package com.iot.controller;

import com.iot.model.resultAndPage.Result;
import com.iot.service.UserService;
import com.iot.util.exception.ExceptionHandle;
import com.iot.util.exception.ResultUtil;
import com.iot.util.myUtil.MyEmailUtil;
import com.iot.util.myUtil.MyVerificationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"#####登陆和注册"})
@RestController
@RequestMapping("/loginAndRegister")
public class LoginAndRegister {
    @Autowired
    private UserService userService;

    @Autowired
    private ExceptionHandle handle;

    @Autowired
    private MyEmailUtil myEmailUtil;

    @Autowired
    private MyVerificationUtil myVerificationUtil;

    @GetMapping("/email")
    @ApiOperation(value = "发送注册邮箱验证邮件", notes = "发送注册邮箱验证邮件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, dataType = "String"),
    })
    public Result email(@RequestParam String email) throws Exception {
        Result result = ResultUtil.success();
        try {
            myVerificationUtil.verifyEmailExit(email);
            myVerificationUtil.verifyEmailOutTime(email);
            myEmailUtil.sendMail(email);
            result = ResultUtil.success();
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @PostMapping("/tokenCheck")
    @ApiOperation(value = "Token验证", notes = "Token验证")
    @ApiImplicitParams({
    })
    public Result tokenCheck() throws Exception {
        Result result = ResultUtil.success();
        try {
            result = ResultUtil.success();
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "email", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "emailCode", value = "验证码", required = true, dataType = "String"),
    })
    public Result insertUser(@RequestParam String email, @RequestParam String password, @RequestParam String emailCode) throws Exception {
        Result result = ResultUtil.success();
        try {
            myVerificationUtil.verifyEmialCode(email, emailCode);
            result = ResultUtil.success(userService.insertUser(email, password, "用户"));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @PostMapping("/register_Admin")
    @ApiOperation(value = "注册（管理员）", notes = "注册（管理员）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "email", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "emailCode", value = "验证码", required = true, dataType = "String"),
    })
    public Result register_Admin(@RequestParam String email, @RequestParam String password, @RequestParam String emailCode) throws Exception {
        Result result = ResultUtil.success();
        try {
            myVerificationUtil.verifyEmialCode(email, emailCode);
            result = ResultUtil.success(userService.insertUser(email, password, "管理员"));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @PostMapping("/loginCheck")
    @ApiOperation(value = "登录", notes = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "email", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
    })
    public Result login(@RequestParam String email, @RequestParam String password) throws Exception {
        Result result = ResultUtil.success();
        try {
            result = ResultUtil.success(userService.login(email, password));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }
}
