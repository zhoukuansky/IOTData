package com.iot.service.impl;

import com.github.pagehelper.PageHelper;
import com.iot.dao.UserMapper;
import com.iot.framework.aop.SystemServiceLog;
import com.iot.model.User;
import com.iot.model.resultAndPage.PageResultBean;
import com.iot.service.UserService;
import com.iot.util.authentication.JwtToken;
import com.iot.util.exception.DescribeException;
import com.iot.util.exception.ExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Override
    @SystemServiceLog(logAction = "register", logContent = "用户注册")
    public  Map insertUser(String tel, String password, String role){
        Map result = new HashMap();
        if(!role.equals("管理员")&&!role.equals("用户")){
            throw new DescribeException(ExceptionEnum.ROLE_SELECT_ERROR);
        }
        if (null==tel||null==password||null==role){
            throw new DescribeException(ExceptionEnum.INCOMPLETE_REGISTRATION_INFORMATION);
        }
        userMapper.insert(tel,password,role);
        //这里采用result<Map>方式返回，而非直接返回的原因是aop日志，可以直接一个函数处理登录和注册，以免再写一个函数
        result.put("user",userService.queryUserByTel(tel));
        return result;
    }

    @Override
    public User queryUserByTel(String tel){
        return userMapper.selectByTel(tel);
    }

    @Override
    @SystemServiceLog(logAction = "login", logContent = "用户登陆")
    public Map login(String tel, String password){
        Map loginMap = new HashMap();
        Map result = new HashMap();
        User loginUser=userMapper.login(tel,password);
        if (loginUser == null) {
            throw new DescribeException(ExceptionEnum.LOGIN_ERROR);
        }
        loginMap.put("id", loginUser.getId());
        loginMap.put("role", loginUser.getRole());
        result.put("token", JwtToken.createToken(loginMap));
        result.put("user",loginUser);
        return result;
    }

    @Override
    public Object queryUserInformation(int id){
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public Object queryAllUser_Admin(int pageNum, int pageSize, String orderBy, String sort){
        String order = orderBy + " " + sort;
        PageHelper.startPage(pageNum, pageSize, order);
        return new PageResultBean<User>(userMapper.queryAllUser_Admin());
    }

    @Override
    public Object updateUserInformation(String name, String email, String address, int id){
        User user=new User();
        user.setId(id);
        user.setAddress(address);
        user.setEmail(email);
        user.setName(name);
        userMapper.updateByPrimaryKeySelective(user);
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updatePassword(String password, int id){
        userMapper.updatePassword(password,id);
        return;
    }

    @Override
    public Object deleteUserAccount(String password, int userId){
        userService.verifyPassword(password,userId);
        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public boolean verifyPassword(String password,int userId){
        User user=userMapper.verifyPassword(userId,password);
        if (user == null) {
            throw new DescribeException(ExceptionEnum.PASSWORD_ERRPOR);
        }
        return true;
    }

    @Override
    public Object deleteDataType_Admin(int id,String password,int[] ids){
        userService.verifyPassword(password,id);
        return userMapper.deleteDataType_Admin(ids);
    }
}
