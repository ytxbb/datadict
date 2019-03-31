package com.jf.datadict.dao;

import com.jf.datadict.model.IndexTableStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataStatisticsMapper {

    List<IndexTableStatistics> queryDataStatistics();
}
