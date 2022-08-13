package com.iot.dao;

import com.iot.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(String email, String password, String role, String apiKey);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByEmail(String email);

    User selectByTel(String tel);

    User login(String email, String password);

    void updatePassword(String password, int id);

    List<User> queryAllUser_Admin();

    User verifyPassword(int id, String password);

    int deleteUser_Admin(int[] ids);

    User verifyApiKey(String apiKey);

    User queryApiKey(int id);

    void updateApiKey(int id, String apiKey);

}