package com.jf.datadict.service.impl;

import com.jf.datadict.dao.DataBaseMapper;
import com.jf.datadict.entity.DataBaseName;
import com.jf.datadict.exception.ServiceException;
import com.jf.datadict.model.JSONResult;
import com.jf.datadict.service.DataBaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataBaseServiceImpl implements DataBaseService {

    @Resource
    private DataBaseMapper dataBaseMapper;

    @Override
    public JSONResult queryAllDataBase() {
        List<DataBaseName> dataBaseNames;
        try {
            dataBaseNames = dataBaseMapper.queryAllDataBase();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("查询数据库列表出错："+e.getMessage());
        }
        return JSONResult.ok(dataBaseNames);
    }
}
