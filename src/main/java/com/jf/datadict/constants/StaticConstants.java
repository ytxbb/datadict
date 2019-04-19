package com.jf.datadict.constants;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 静态集合
 */
public class StaticConstants {

    public static final Set<String> mysqlDefaultDBName = new HashSet<String>(){{
        add("information_schema");
        add("mysql");
        add("performance_schema");
        add("sys");
    }};

    /**
     * 存储mysql表单提交过来的数据库连接信息
     */
    public static HashMap<String, String> databaseInfoMapOfMysql = new HashMap<>();
}
