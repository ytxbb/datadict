package com.jf.datadict.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MySqlTable {

    private String title;// 表名称

    private String tableName;// 数据库表名

    private int fieldCount = 7;

    private String[] fieldValues = null;// 表字段值
    private List<String[]> fieldList = new ArrayList<>();
}
