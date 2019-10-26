package com.jf.datadict.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 通用数据库连接
 */
@Getter
@Setter
public class DataBaseVO {
    private Integer dbSupprot;
    private String ip;
    private Integer port;
    private String userName;
    private String password;
}
