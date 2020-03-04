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
    private DataTypeMapper dataTypeMapper;

    @Autowired
    private DirectionMapper directionMapper;

    @Autowired
    private OperationMapper operationMapper;

    @Autowired
    private AdminService adminService;

    /**
     * 传感器数据类型设置
     */
    @Override
    public Object queryAllDataType(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum, pageSize, "id ASC");
        return new PageResultBean<DataType>(dataTypeMapper.queryAllDataType());
    }

    @Override
    public synchronized Object   insertDataType_Admin(String word) {
        dataTypeMapper.insert(word);
        return adminService.queryAllDataType(1,100);
    }

    @Override
    public Object deleteDataType_Admin(int[] ids) {
        return dataTypeMapper.deleteDataType_Admin(ids);
    }

    @Override
    public Object queryOneDataType_Admin(int id){
        return dataTypeMapper.selectByPrimaryKey(id);
    }

    /**
     * 系统使用方向选择设置
     */
    @Override
    public Object queryAllDirectionType(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, "id ASC");
        return new PageResultBean<Direction>(directionMapper.queryAllDirectionType());
    }

    @Override
    public synchronized Object insertDirectionType_Admin(String word) {
        directionMapper.insert(word);
        return adminService.queryAllDirectionType(1,100);
    }

    @Override
    public Object deleteDirectionType_Admin(int[] ids) {
        return directionMapper.deleteDirectionType_Admin(ids);
    }

    @Override
    public Object queryOneDirectionType_Admin(int id){
        return directionMapper.selectByPrimaryKey(id);
    }

    /**
     * 系统操作系统类型设置
     */
    @Override
    public Object queryAllOperationType(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, "id ASC");
        return new PageResultBean<Direction>(operationMapper.queryAllOperationType());
    }

    @Override
    public synchronized Object insertOperationType_Admin(String word) {
        operationMapper.insert(word);
        return adminService.queryAllOperationType(1,100);
    }

    @Override
    public Object deleteOperationType_Admin(int[] ids) {
        return operationMapper.deleteOperationType_Admin(ids);
    }

    @Override
    public Object queryOneOperationType_Admin(int id){
        return operationMapper.selectByPrimaryKey(id);
    }
}
