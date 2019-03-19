package com.jf.datadict.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 表与分类 关系表
 */
@Getter
@Setter
public class DictRelTableType {

    private Integer uid;
    private String tableId;
    private String dtId;
    private String version;
}
