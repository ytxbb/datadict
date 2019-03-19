package com.jf.datadict.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 表结构
 */
@Getter
@Setter
public class DictTableStructure {

    private Integer uid;
    private String field;
    private String type;
    private String length;
    private String isNull;
    private String isKey;
    private String defaultValue;
    private String comment;
    private String remark;
    private String version;
    private Integer status;
}
