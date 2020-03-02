package com.iot.controller;

import com.iot.framework.aop.SystemControllerLog;
import com.iot.model.acceptParam.IdsOfDelete;
import com.iot.model.resultAndPage.Result;
import com.iot.service.UserService;
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

@Api(tags = {"用户账户操作"})
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ExceptionHandle handle;

    @GetMapping("/queryUserInformation")
    @SystemControllerLog(logAction = "userInformation", logContent = "用户查看自己信息")
    @ApiOperation(value = "用户查看自己信息", notes = "用户查看自己信息")
    @ApiImplicitParams({
    })
    public Result userInformation(@CurrentUser Map tokenData) throws Exception{
        Result result = ResultUtil.success();
        int id=(int) tokenData.get("id");
        try {
            result = ResultUtil.success(userService.userInformation(id));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @GetMapping("/queryAllUser_Admin")
    @SystemControllerLog(logAction = "adminQueryAllUser", logContent = "管理员查看所有用户数据 ")
    @ApiOperation(value = "管理员查看所有用户数据", notes = "管理员查看所有用户数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "此项可不填：不填时查看所有日志记录，填时则单独查看某用户日志记录", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pageNum", value = "页数（第几页），不填默认为1", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小,默认为10", required = false, dataType = "String"),
            @ApiImplicitParam(name = "orderBy", value = "排序目标（按照那个属性排序），不填默认“id”", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "正序还是倒序（只有两种选择：“ASC”或者“DESC”），不填默认ASC", required = false, dataType = "String"),
    })
    public Result adminQueryAllUser(@RequestParam(value = "userId",defaultValue ="-1",required = false) Integer userId,@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam(value = "orderBy", defaultValue = "id") String orderBy, @RequestParam(value = "sort", defaultValue = "ASC") String sort, @CurrentUser Map tokenData) throws Exception{
        Result result = ResultUtil.success();
        try {
            if (-1==userId){
                result = ResultUtil.success(userService.adminQueryAllUser(pageNum, pageSize, orderBy, sort));
            }else {
                result = ResultUtil.success(userService.userInformation(userId));
            }
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @PutMapping("/updateUserInformation")
    @SystemControllerLog(logAction = "updateUserInformation", logContent = "普通用户更新自身信息")
    @ApiOperation(value = "普通用户更新自身信息", notes = "普通用户更新自身信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户昵称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = false, dataType = "String"),
            @ApiImplicitParam(name = "address", value = "地址", required = false, dataType = "String"),
    })
    public Result updateUserInformation(@RequestParam(value = "name",required = false) String name,@RequestParam(value = "email",required = false) String email,@RequestParam(value = "address",required = false) String address, @CurrentUser Map tokenData) throws Exception{
        Result result = ResultUtil.success();
        int id = (int)tokenData.get("id");
        try {
            result = ResultUtil.success(userService.updateUserInformation(name,email,address,id));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @PutMapping("/updatePassword")
    @SystemControllerLog(logAction = "updatePassword", logContent = "修改密码")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
    })
    public Result updatePassword(@RequestParam("password") String password, @CurrentUser Map tokenData) throws Exception{
        Result result = ResultUtil.success();
        int id = (int)tokenData.get("id");
        try {
            userService.updatePassword(password, id);
            result = ResultUtil.success();
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @DeleteMapping("/deleteUserAccount")
    @SystemControllerLog(logAction = "userDeleteAccount", logContent = "用户注销账户")
    @ApiOperation(value = "用户注销账户", notes = "用户注销账户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "请输入密码", required = true, dataType = "String"),
    })
    public Result userDeleteAccount(@RequestParam("password") String password, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        int userId = (int) tokenData.get("id");
        try {
            result = ResultUtil.success(userService.userDeleteAccount(password, userId));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @DeleteMapping("/deleteAccount_Admin")
    @SystemControllerLog(logAction = "adminDeleteAccount", logContent = "管理员批量删除账户")
    @ApiOperation(value = "管理员批量删除账户", notes = "管理员批量删除账户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "请输入管理员账户密码", required = true, dataType = "String"),
    })
    public Result adminDeleteAccount(@RequestParam("password") String password,@RequestBody IdsOfDelete idsOfDelete, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        VerificationUtil.adminVerification(tokenData);
        int id = (int) tokenData.get("id");
        try {
            result = ResultUtil.success(userService.adminDeleteAccount(id,password,idsOfDelete.getIds()));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }
}
