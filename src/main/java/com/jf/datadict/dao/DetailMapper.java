package com.jf.datadict.dao;

import com.jf.datadict.entity.DictMenu;
import com.jf.datadict.entity.DictTableStructure;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DetailMapper {

    /**
     * 查询所有数据库
     */
    List<String> queryAllDataBase();

    /**
     * 查询菜单列表
     */
    List<DictMenu> queryMenuList(@Param("dbName") String dbName);

    /**
     * 查询表结构
     */
    List<DictTableStructure> queryTableStructure(@Param("dataBaseName") String dataBaseName, @Param("tableName") String tableName);

    /**
     * 查询表字段个数
     */
    List<DictTableStructure> queryTableColumnCount(@Param("dataBaseName") String dataBaseName);

    /**
     * 查询表名注释
     */
    List<DictTableStructure> queryTableComment(@Param("dataBaseName") String dataBaseName);
}
