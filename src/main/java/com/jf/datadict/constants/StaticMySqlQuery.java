package com.jf.datadict.constants;

import com.jf.datadict.model.MySqlVO;
import com.jf.datadict.util.MyStringUtil;

public class StaticMySqlQuery {

    public static final String dbListQuery = "select schema_name db_name from information_schema.schemata";

    public static String getTablesQuery(String dbName) {
        return "select table_name,count(table_name) c from information_schema.columns where table_schema ="+dbName+" group by table_name";
    }

    public static String getMysqlUrl(MySqlVO vo){
        if (MyStringUtil.isHasEmpty(vo.getIp(), vo.getPort())) {
            return null;
        }
        return "jdbc:mysql://" + vo.getIp() + ":" + vo.getPort() + "/mysql?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false";
    }
}
