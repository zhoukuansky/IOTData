package com.iot.controller;

import com.iot.framework.aop.SystemControllerLog;
import com.iot.model.resultAndPage.Result;
import com.iot.service.UserService;
import com.iot.util.authentication.CurrentUser;
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

import java.util.Map;

@Api(tags = {"用户——账户"})
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ExceptionHandle handle;

    @Autowired
    private MyVerificationUtil myVerificationUtil;

    @Autowired
    private MyEmailUtil myEmailUtil;

    @GetMapping("/queryUserInformation")
    @SystemControllerLog(logAction = "queryUserInformation", logContent = "用户查看自己信息")
    @ApiOperation(value = "用户查看自己信息", notes = "用户查看自己信息")
    @ApiImplicitParams({
    })
    public Result queryUserInformation(@CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int id = (int) tokenData.get("id");
            result = ResultUtil.success(userService.queryUserInformation(id));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }


    @GetMapping("/queryAllUser_Admin")
    @SystemControllerLog(logAction = "queryAllUser_Admin", logContent = "查看所有用户数据（管理员）")
    @ApiOperation(value = "查看所有用户数据（管理员）", notes = "查看所有用户数据（管理员）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "此项可不填：不填时查看所有用户信息，填时则单独查看某用户信息", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pageNum", value = "第几页（不填默认为1)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小(不填默认为10)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "orderBy", value = "排序属性(不填默认“id”)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "正序或倒序[“ASC”或者“DESC”]（默认ASC）", required = false, dataType = "String"),
    })
    public Result queryAllUser_Admin(@RequestParam(value = "userId", defaultValue = "-1", required = false) Integer userId, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam(value = "orderBy", defaultValue = "id") String orderBy, @RequestParam(value = "sort", defaultValue = "ASC") String sort, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            myVerificationUtil.adminVerification(tokenData);
            myVerificationUtil.sortVerification(sort);
            if (-1 == userId) {
                result = ResultUtil.success(userService.queryAllUser_Admin(pageNum, pageSize, orderBy, sort));
            } else {
                result = ResultUtil.success(userService.queryUserInformation(userId));
            }
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @PutMapping("/updateUserInformation")
    @SystemControllerLog(logAction = "updateUserInformation", logContent = "用户更新自身信息")
    @ApiOperation(value = "用户更新自身信息", notes = "用户更新自身信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户昵称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "tel", value = "手机", required = false, dataType = "String"),
            @ApiImplicitParam(name = "address", value = "地址", required = false, dataType = "String"),
    })
    public Result updateUserInformation(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "tel", required = false) String tel, @RequestParam(value = "address", required = false) String address, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int id = (int) tokenData.get("id");
            if (null != tel) {
                myVerificationUtil.verifyTelExit(tel);
            }
            result = ResultUtil.success(userService.updateUserInformation(name, tel, address, id));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @PutMapping("/updatePassword")
    @SystemControllerLog(logAction = "updatePassword", logContent = "修改密码")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "旧密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "新密码", required = true, dataType = "String"),
    })
    public Result updatePassword(@RequestParam String oldPassword, @RequestParam("password") String password, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int id = (int) tokenData.get("id");
            myVerificationUtil.verifyPassword(oldPassword, id);
            userService.updatePassword(password, id);
            result = ResultUtil.success();
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

    @DeleteMapping("/deleteUserAccount")
    @SystemControllerLog(logAction = "deleteUserAccount", logContent = "用户注销账户")
    @ApiOperation(value = "用户注销账户", notes = "用户注销账户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "emailCode", value = "验证码", required = true, dataType = "String"),
    })
    public Result deleteUserAccount(@RequestParam String emailCode, @CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int userId = (int) tokenData.get("id");
            String email = (String) tokenData.get("email");
            myVerificationUtil.verifyEmialCode(email, emailCode);
            result = ResultUtil.success(userService.deleteUserAccount(userId));
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }

//    @DeleteMapping("/deleteUser_Admin")
//    @SystemControllerLog(logAction = "deleteUser_Admin", logContent = "批量删除账户（管理员）")
//    @ApiOperation(value = "批量删除账户（管理员）", notes = "批量删除账户（管理员）")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "emailCode", value = "验证码", required = true, dataType = "String"),
//            @ApiImplicitParam(name = "ids[]", value = "需要删除的id数组", required = true, allowMultiple = true, dataType = "int"),
//    })
//    public Result deleteUser_Admin(@RequestParam String emailCode, @RequestParam(value = "ids[]") int[] ids, @CurrentUser Map tokenData) throws Exception {
//        Result result = ResultUtil.success();
//        try {
//            int userId = (int) tokenData.get("id");
//            String email = (String) tokenData.get("email");
//            myVerificationUtil.adminVerification(tokenData);
//            myVerificationUtil.verifyEmialCode(email, emailCode);
//            result = ResultUtil.success(userService.deleteUser_Admin(ids));
//        } catch (Exception e) {
//            result = handle.exceptionGet(e);
//        }
//        return result;
//    }

    @GetMapping("/emailDelete")
    @ApiOperation(value = "发送注销账户验证邮件", notes = "发送注销账户验证邮件")
    @ApiImplicitParams({
    })
    public Result email(@CurrentUser Map tokenData) throws Exception {
        Result result = ResultUtil.success();
        try {
            int userId = (int) tokenData.get("id");
            String email = (String) tokenData.get("email");
            myVerificationUtil.verifyEmailOutTime(email);
            myEmailUtil.sendMail(email);
            result = ResultUtil.success();
        } catch (Exception e) {
            result = handle.exceptionGet(e);
        }
        return result;
    }
}
