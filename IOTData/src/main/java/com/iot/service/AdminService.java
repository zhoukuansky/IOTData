package com.iot.service;

import com.iot.model.DataType;

import java.util.List;

public interface AdminService {
    List<DataType> adminQueryAllDataType();

    List<DataType> adminInsertDataType(String word);
}
