package com.jf.datadict.test;

import com.jf.datadict.exception.ServiceException;
import com.jf.datadict.model.MySqlVO;
import com.jf.datadict.util.DBUtils;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Test {

    private static String driverClassName = "com.mysql.jdbc.Driver";

    public static void main(String[] args) {
        MySqlVO vo = new MySqlVO();
        vo.setIp("1270.123.3.3");
        vo.setPort("3306");
        vo.setUserName("root");
        vo.setPwd("tantan");
        Boolean aBoolean = validauteMySqlConnection(vo);
        System.out.println("是否连接成功"+aBoolean);
    }

    public static Boolean validauteMySqlConnection(MySqlVO vo){
        try {
            Class.forName(driverClassName);
            String url = "jdbc:mysql://" + vo.getIp() + ":" + vo.getPort() + "/mysql?useSSL=false";
            Connection conn= DriverManager.getConnection(url,vo.getUserName(),vo.getPwd());
            System.out.println(conn);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServiceException("数据库尝试连接失败！");
        }
        return true;
    }
}
