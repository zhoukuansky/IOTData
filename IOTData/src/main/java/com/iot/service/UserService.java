package com.iot.service;

import com.iot.model.User;

import java.util.Map;

public interface UserService {
    Map insertUser(String tel, String password, String role);

    User queryUserByTel(String tel);

    Map login(String tel, String password);

    Object queryUserInformation(int id);

    Object updateUserInformation(String name, String email, String address, int id);

    void updatePassword(String password, int id);

    Object queryAllUser_Admin(int pageNum, int pageSize, String orderBy, String sort);

    Object deleteUserAccount(int userId);

    User verifyPassword(String password, int userId);

    Object deleteUser_Admin(int[] ids);

    User verifyApiKey(String apiKey);

    Map queryApiKey(int userId);

    Map updateApiKey(int userId);
}
