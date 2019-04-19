package com.jf.datadict.config;

import com.jf.datadict.constants.StaticConstants;
import com.jf.datadict.util.DBUtils;
import com.jf.datadict.util.MyJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

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
        StaticConstants.databaseInfoMapOfMysql.put("url", "jdbc:mysql://127.0.0.1:3306/jf_data_dict?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false");
        StaticConstants.databaseInfoMapOfMysql.put("username", "root");
        StaticConstants.databaseInfoMapOfMysql.put("password", "tantan");


        try {
            ResultSet rs = DBUtils.query("select * from dict_type");
            while (rs.next()) {
                System.out.println(rs.getInt(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
                System.out.println(rs.getString(4));
                System.out.println("------");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}  
