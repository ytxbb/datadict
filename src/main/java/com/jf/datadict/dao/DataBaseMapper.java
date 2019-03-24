package com.jf.datadict.dao;

import com.jf.datadict.entity.DataBaseName;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataBaseMapper {

    List<DataBaseName> queryAllDataBase();
}
