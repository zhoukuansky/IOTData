package com.iot.service;

import com.iot.model.Log;


public interface LogService {
    Object queryAllLog_Admin(int pageNum, int pageSize, String orderBy, String sort);

    Object queryOneUserLog(int userId, int pageNum, int pageSize, String orderBy, String sort);

    int insert(Log log);

    int deleteOneUserLog(int[] ids, int userId);

    int deleteLog_Admin(int[] ids);
}
