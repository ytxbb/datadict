package com.jf.datadict.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MySqlTable {

    private String dbName;// 数据源名
    private String tableName;// 数据库表名
    private String tableChName;// 数据库表名说明
    private String title;// 表名称
    private int fieldCount = 7;
    private String[] fieldValues = null;// 表字段值
    private List<String[]> fieldList = new ArrayList<>();
}
