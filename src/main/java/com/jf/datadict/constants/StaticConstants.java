package com.jf.datadict.constants;

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
}
