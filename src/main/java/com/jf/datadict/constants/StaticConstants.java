package com.jf.datadict.constants;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 静态集合
 */
public class StaticConstants {

    public static final Set<String> MYSQL_DEFAULT_DBNAME = new HashSet<String>(){{
        add("information_schema");
        add("mysql");
        add("performance_schema");
        add("sys");
    }};

    /**
     * 存储mysql表单提交过来的数据库连接信息
     */
    public static HashMap<String, String> DB_MYSQL_MAP = new HashMap<>();
}
