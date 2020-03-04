package com.iot.service;

/**
 * 此接口主要对只能hi管理员操作的direction、datatype、operation三张表做个统一
 *
 * @author HP
 */
public interface AdminService {
    /**
     * 传感器数据类型设置
     */
    Object queryAllDataType(int pageNum, int pageSize);

    Object insertDataType_Admin(String word);

    Object deleteDataType_Admin(int[] ids);

    Object queryOneDataType_Admin(int id);

    /**
     * 系统使用方向选择设置
     */
    Object queryAllDirectionType(Integer pageNum, Integer pageSize);

    Object insertDirectionType_Admin(String word);

    Object deleteDirectionType_Admin(int[] ids);

    Object queryOneDirectionType_Admin(int id);

    /**
     * 系统操作系统类型设置
     */
    Object queryAllOperationType(Integer pageNum, Integer pageSize);

    Object insertOperationType_Admin(String word);

    Object deleteOperationType_Admin(int[] ids);

    Object queryOneOperationType_Admin(int id);
}
