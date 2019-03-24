package com.jf.datadict.service;

import com.jf.datadict.entity.DataBaseName;

import java.util.List;

public interface DataBaseService {

    /**
     *  查询数据库列表
     */
    List<DataBaseName> queryAllDataBase();
}
