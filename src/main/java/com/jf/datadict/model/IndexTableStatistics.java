package com.jf.datadict.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 默认页面表格统计显示
 */
@Getter
@Setter
public class IndexTableStatistics {

    private String dbName;
    private String version;
    private String dtName;
    private String tableCount;
}
