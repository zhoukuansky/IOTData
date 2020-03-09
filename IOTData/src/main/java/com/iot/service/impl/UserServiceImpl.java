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
import com.iot.util.myUtil.MyStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MyStringUtil myStringUtil;


    @Override
    @SystemServiceLog(logAction = "register", logContent = "用户注册")
    public synchronized Map insertUser(String email, String password, String role) {
        Map result = new HashMap();
        String apiKey = myStringUtil.createApiKey();
        userMapper.insert(email, password, role, apiKey);
        //这里采用result<Map>方式返回，而非直接返回的原因是aop日志，可以直接一个函数处理登录和注册，以免再写一个函数
        result.put("user", userService.queryUserByEmail(email));
        return result;
    }


    @Override
    public User queryUserByTel(String tel) {
        return userMapper.selectByTel(tel);
    }

    @Override
    public User queryUserByEmail(String email) {
        return userMapper.selectByEmail(email);
    }


    @Override
    @SystemServiceLog(logAction = "login", logContent = "用户登陆")
    public Map login(String email, String password) {
        Map loginMap = new HashMap();
        Map result = new HashMap();
        User loginUser = userMapper.login(email, password);
        if (loginUser == null) {
            User user = userService.queryUserByEmail(email);
            if (user == null) {
                throw new DescribeException(ExceptionEnum.USER_NOT_FOUND);
            }
            throw new DescribeException(ExceptionEnum.LOGIN_ERROR);
        }
        loginMap.put("id", loginUser.getId());
        loginMap.put("role", loginUser.getRole());
        loginMap.put("email", loginUser.getRole());
        result.put("token", JwtToken.createToken(loginMap));
        result.put("user", loginUser);
        return result;
    }

    @Override
    public User queryUserInformation(int id) {
        return userMapper.selectByPrimaryKey(id);
    }


    @Override
    public Object queryAllUser_Admin(int pageNum, int pageSize, String orderBy, String sort) {
        String order = orderBy + " " + sort;
        PageHelper.startPage(pageNum, pageSize, order);
        List user=userMapper.queryAllUser_Admin();
        return new PageResultBean<>(user);
    }


    @Override
    public Object updateUserInformation(String name, String tel, String address, int id) {
        User user = new User();
        user.setId(id);
        user.setAddress(address);
        user.setEmail(tel);
        user.setName(name);
        userMapper.updateByPrimaryKeySelective(user);
        return userMapper.selectByPrimaryKey(id);
    }


    @Override
    public void updatePassword(String password, int id) {
        userMapper.updatePassword(password, id);
        return;
    }


    @Caching(evict={
            @CacheEvict(value = "UserInfoCache", key ="'user_'+#userId"),
    })
    @Override
    public Object deleteUserAccount(int userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }


    @Override
    public User verifyPassword(String password, int userId) {
        User user = userMapper.verifyPassword(userId, password);
        return user;
    }


    @Override
    public Object deleteUser_Admin(int[] ids) {
        return userMapper.deleteUser_Admin(ids);
    }


    @Override
    public User verifyApiKey(String apiKey) {
        return userMapper.verifyApiKey(apiKey);
    }

    @Cacheable(value = "UserInfoCache",key = "'ApiKey_'+#userId")
    @Override
    public Map queryApiKey(int userId) {
        User user = userMapper.queryApiKey(userId);
        Map<String, String> apiMap = new HashMap<String, String>();
        apiMap.put("apiKey", user.getApiKey());
        return apiMap;
    }

    @CacheEvict(value = "UserInfoCache",key = "'ApiKey_'+#userId",beforeInvocation = true)
    @Override
    public Map updateApiKey(int userId) {
        String apiKey = myStringUtil.createApiKey();
        userMapper.updateApiKey(userId, apiKey);
        return userService.queryApiKey(userId);
    }
}
