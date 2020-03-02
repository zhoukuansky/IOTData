package com.iot.service.impl;

import com.github.pagehelper.PageHelper;
import com.iot.dao.LogMapper;
import com.iot.model.Log;
import com.iot.model.resultAndPage.PageResultBean;
import com.iot.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public Object adminQueryAllLog(int pageNum, int pageSize, String orderBy, String sort) {
        String order = orderBy + " " + sort;
        PageHelper.startPage(pageNum, pageSize, order);
        return new PageResultBean<Log>(logMapper.adminQueryAllLog());
    }

    @Override
    public Object userQueryHisLog(int userId, int pageNum, int pageSize, String orderBy, String sort) {
        String order = orderBy + " " + sort;
        PageHelper.startPage(pageNum, pageSize, order);
        return new PageResultBean<Log>(logMapper.userQueryHisLog(userId));
    }

    @Override
    public int insert(Log log) {
        int i = logMapper.insert(log);
        return i;
    }

    @Override
    public int userDeleteHisLog(int[] ids, int userId) {
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("userId", userId);
        params.put("ids", ids);
        return logMapper.userDeleteHisLog(params);
    }

    @Override
    public int adminDeleteLog(int[] ids) {
        return logMapper.adminDeleteLog(ids);
    }
}
