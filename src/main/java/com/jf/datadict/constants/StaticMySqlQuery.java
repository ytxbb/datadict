package com.jf.datadict.constants;

import com.jf.datadict.model.MySqlVO;
import com.jf.datadict.util.MyStringUtil;

public class StaticMySqlQuery {

    public static final String dbListQuery = "select schema_name db_name from information_schema.schemata";

    public static String getTablesQuery(String dbName) {
        return "select table_name,count(table_name) c from information_schema.columns where table_schema = '"+dbName+"' group by table_name";
    }

    public static String countField(String dbName) {
        return "select table_name t,count(table_name) c from information_schema.columns where table_schema = '"+dbName+"' group by t";
    }

    public static String getTableComment(String dbName) {
        return "select table_name tn,table_comment tc from information_schema.tables where table_schema = '"+dbName+"'";
    }

    public static String getTableFieldDetail(String dbName, String realTableName) {
        String sql = "select table_name,column_name,column_type,is_nullable,column_key,column_default,column_comment from information_schema.columns " +
                "where table_schema = '"+dbName+"'";
        if (realTableName != null) {
            sql += "and table_name = '"+realTableName+"'";
        }
        return sql;
    }

    public static String getMysqlUrl(MySqlVO vo){
        if (MyStringUtil.isHasEmpty(vo.getIp(), vo.getPort())) {
            return null;
        }
        return "jdbc:mysql://" + vo.getIp() + ":" + vo.getPort() + "/mysql?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false";
    }
}
