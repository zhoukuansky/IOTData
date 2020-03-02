package com.iot.service;

import com.iot.model.Log;


public interface LogService {
    public Object adminQueryAllLog(int pageNum, int pageSize, String orderBy, String sort);

    public Object userQueryHisLog(int userId, int pageNum, int pageSize, String orderBy, String sort);

    public int insert(Log log);

    public int userDeleteHisLog(int[] ids, int userId);

    public int adminDeleteLog(int[] ids);
}
