package com.jf.datadict.constants;

import java.util.HashSet;
import java.util.Set;

/**
 * 静态集合
 */
public class StaticConstants {

    public static final Set<String> MYSQL_DEFAULT_DBNAME = new HashSet<String>() {{
        add("information_schema");
        add("mysql");
        add("performance_schema");
        add("sys");
    }};
}
