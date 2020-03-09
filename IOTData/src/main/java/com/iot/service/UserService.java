package com.iot.service;

import com.iot.model.User;

import java.util.Map;

public interface UserService {
    Map insertUser(String email, String password, String role);

    User queryUserByEmail(String email);

    User queryUserByTel(String tel);

    Map login(String email, String password);

    User queryUserInformation(int id);

    Object updateUserInformation(String name, String tel, String address, int id);

    void updatePassword(String password, int id);

    Object queryAllUser_Admin(int pageNum, int pageSize, String orderBy, String sort);

    Object deleteUserAccount(int userId);

    User verifyPassword(String password, int userId);

    Object deleteUser_Admin(int[] ids);

    User verifyApiKey(String apiKey);

    Map queryApiKey(int userId);

    Map updateApiKey(int userId);
}
