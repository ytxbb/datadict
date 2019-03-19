package com.jf.datadict.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 菜单
 */
@Getter
@Setter
public class DictMenu {

    private Integer uid;
    private String dbId;
    private String tableId;
    private String parentUid;
    private String sortNo;
    private String version;
    private Integer status;
    private Date createTime;
    private Timestamp updateTime;
}
