package com.jf.datadict.service.impl;

import com.jf.datadict.dao.DataStatisticsMapper;
import com.jf.datadict.exception.ServiceException;
import com.jf.datadict.model.IndexTableStatistics;
import com.jf.datadict.model.JSONResult;
import com.jf.datadict.service.DataStatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataStatisticsServiceImpl implements DataStatisticsService {

    @Resource
    private DataStatisticsMapper dataStatisticsMapper;

    @Override
    public JSONResult queryDataStatistics() {
        List<IndexTableStatistics> resList;
        try {
            resList = dataStatisticsMapper.queryDataStatistics();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("查询数据库列表出错："+e.getMessage());
        }
        return JSONResult.ok(resList);
    }
}
