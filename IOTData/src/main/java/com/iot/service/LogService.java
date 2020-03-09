package com.iot.service;

import com.iot.model.Log;


public interface LogService {
    public Object queryAllLog_Admin(int pageNum, int pageSize, String orderBy, String sort);

    public Object queryOneUserLog(int userId, int pageNum, int pageSize, String orderBy, String sort);

    public int insert(Log log);

    public int deleteOneUserLog(int[] ids, int userId);

    public int deleteLog_Admin(int[] ids);
}
