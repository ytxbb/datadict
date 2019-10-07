package com.jf.datadict.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 表结构
 */
@Getter
@Setter
public class DictTableStructure {

    private String tableName;
    private String field;
    private String type;
    private String length;
    private String isNull;
    private String key;
    private String defaultValue;
    private String comment;

    private Integer fieldCount;// 字段数
    private String tableChName;// 汉语表名
}
