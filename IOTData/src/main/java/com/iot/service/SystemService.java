package com.iot.service;

import com.iot.model.Systems;
import com.iot.model.acceptParam.SystemParam;

import java.util.List;

public interface SystemService {
    List<Systems> queryUserSystemInformationByUserId(int userId);

    Object queryOneSystemInformation(Integer systemId);

    Object queryAllSystemInformation_Admin(Integer pageNum, Integer pageSize, String orderBy, String sort);

    Object insertSystem(SystemParam systemParam);

    Object updateSystemInformation(SystemParam systemParam);

    Object deleteSystem(int userId, int systemId);

    Systems verifySystemInUser(int userId, int systemId);
}
