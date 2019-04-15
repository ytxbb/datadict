package com.jf.datadict.service;

import com.jf.datadict.model.JSONResult;

public interface DetailService {

    /**
     *  查询数据库列表
     */
    JSONResult queryAllDataBase();

    /**
     * 根据名称查询数据源
     */
    JSONResult queryMenuList(String dbName);

    /**
     * 查询表结构
     */
    JSONResult queryTableStructure(String dataBaseName,String tableName);
}
