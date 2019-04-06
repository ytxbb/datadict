package com.jf.datadict.dao;

import com.jf.datadict.entity.DataBaseName;
import com.jf.datadict.entity.DictMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DetailMapper {

    List<DataBaseName> queryAllDataBase();

    DataBaseName queryAllDataBaseByName(@Param("dbName") String dbName);

    List<DictMenu> queryMenuList(@Param("dbId") Integer dbId);
}
