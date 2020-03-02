package com.iot.controller;

import com.iot.model.resultAndPage.Result;
import com.iot.service.UserService;
import com.iot.util.exception.ExceptionHandle;
import com.iot.util.exception.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = {"登陆和注册"})
@RestController
@RequestMapping("/loginAndRegister")
public class LoginAndRegister {
    @Autowired
    private UserService userService;

    @Autowired
    private ExceptionHandle handle;

    @PostMapping("/register")
    @ApiOperation(value = "注册用户信息", notes = "注册用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "用户tel", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "role", value = "账户角色，只有两种选择“用户”或者“管理员”", required = true, dataType = "String"),
    })
    public Result insertUser(@RequestParam("tel") String tel, @RequestParam("password") String password, @RequestParam("role") String role) throws Exception{
        Result result = ResultUtil.success();
        try {
            result = ResultUtil.success(userService.insertUser(tel, password, role));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @GetMapping("/login")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "用户tel", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
    })
    public Result login(@RequestParam("tel") String tel, @RequestParam("password") String password) throws Exception{
        Result result = ResultUtil.success();
        try {
            result = ResultUtil.success(userService.login(tel, password));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }
}
