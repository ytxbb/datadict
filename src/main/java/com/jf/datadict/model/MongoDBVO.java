package com.jf.datadict.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 表单填写所传VO
 */
@Getter
@Setter
public class MongoDBVO {
    private String mongodbIp;
    private Integer mongodbPort;
    private String mongodbuserName;
    private String mongodbPassword;
}
