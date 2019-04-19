package com.jf.datadict.config;

import com.jf.datadict.constants.StaticConstants;
import com.jf.datadict.util.DBUtils;

import java.sql.ResultSet;

public class ConnectionPoolTest {   
  
    /*public static void main(String[] args) throws Exception {
        ConnectionPool connPool = new ConnectionPool("com.mysql.jdbc.Driver","jdbc:mysql://127.0.0.1:3306/jf_data_dict?useSSL=false","root","tantan");
        connPool.createPool();   
        Connection conn = connPool.getConnection();   
        connPool.closeConnectionPool();   
        connPool.setTestTable("EMP");   
    }*/

    /*public static void main(String[] args) throws Exception {
        ConnectionPool connPool = new ConnectionPool("com.mysql.jdbc.Driver","jdbc:mysql://127.0.0.1:3306/jf_data_dict?useSSL=false","root","tantan");
        connPool.createPool();
        System.out.println("**********");
        Connection conn = connPool.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from dict_type");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getInt(1));
            System.out.println(rs.getString(2));
            System.out.println(rs.getString(3));
            System.out.println(rs.getString(4));
            System.out.println("------");
        }
        connPool.closeConnectionPool();
        connPool.setTestTable("EMP");
    }*/

    public static void main(String[] args) {
        String ip = "192.168.0.11";
        String port = "3306";
        String url = "jdbc:mysql://"+ip+":"+port+"/mysql?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false";
//        StaticConstants.DB_MYSQL_MAP.put("url", "jdbc:mysql://192.168.0.11:3306/mysql?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false");
        StaticConstants.DB_MYSQL_MAP.put("url",url);
        StaticConstants.DB_MYSQL_MAP.put("username", "root");
        StaticConstants.DB_MYSQL_MAP.put("password", "jfkjyfb");


        try {
            ResultSet rs = DBUtils.query("select schema_name db_name from information_schema.schemata");
            /*while (rs.next()) {
                System.out.println(rs.getString(1) +"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4));
                System.out.println("------");
            }*/
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}  
