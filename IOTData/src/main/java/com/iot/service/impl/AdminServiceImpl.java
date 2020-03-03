package com.iot.service.impl;

import com.github.pagehelper.PageHelper;
import com.iot.dao.DataTypeMapper;
import com.iot.dao.DirectionMapper;
import com.iot.dao.OperationMapper;
import com.iot.model.DataType;
import com.iot.model.Direction;
import com.iot.model.resultAndPage.PageResultBean;
import com.iot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    DataTypeMapper dataTypeMapper;

    @Autowired
    DirectionMapper directionMapper;

    @Autowired
    OperationMapper operationMapper;

    @Autowired
    AdminService adminService;

    /**
     * 传感器数据类型设置
     */
    @Override
    public Object queryAllDataType_Admin(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum, pageSize, "id ASC");
        return new PageResultBean<DataType>(dataTypeMapper.queryAllDataType_Admin());
    }

    @Override
    public Object insertDataType_Admin(String word) {
        dataTypeMapper.insert(word);
        return adminService.queryAllDataType_Admin(1,100);
    }

    @Override
    public Object deleteDataType_Admin(int[] ids) {
        return dataTypeMapper.deleteDataType_Admin(ids);
    }

    /**
     * 系统使用方向选择设置
     */
    @Override
    public Object queryAllDirectionType_Admin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, "id ASC");
        return new PageResultBean<Direction>(directionMapper.queryAllDirectionType_Admin());
    }

    @Override
    public Object insertDirectionType_Admin(String word) {
        directionMapper.insert(word);
        return adminService.queryAllDirectionType_Admin(1,100);
    }

    @Override
    public Object deleteDirectionType_Admin(int[] ids) {
        return directionMapper.deleteDirectionType_Admin(ids);
    }

    /**
     * 系统操作系统类型设置
     */
    @Override
    public Object queryAllOperationType_Admin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, "id ASC");
        return new PageResultBean<Direction>(operationMapper.queryAllOperationType_Admin());
    }

    @Override
    public Object insertOperationType_Admin(String word) {
        operationMapper.insert(word);
        return adminService.queryAllOperationType_Admin(1,100);
    }

    @Override
    public Object deleteOperationType_Admin(int[] ids) {
        return operationMapper.deleteOperationType_Admin(ids);
    }
}
