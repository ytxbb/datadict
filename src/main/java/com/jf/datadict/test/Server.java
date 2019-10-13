package com.jf.datadict.test;

import java.io.*;
import java.net.*;
import java.sql.*;

/**
 * 原文链接：https://blog.csdn.net/qq1922631820/article/details/86405029
 */
public class Server {
    public static void main(String args[]) {
        while (true) {
            //socket
            ServerSocket server = null;
            Socket you = null;
            try {
                server = new ServerSocket(7000);
            } catch (IOException el) {
                System.out.println("正在监听");
            }
            try {
                System.out.println("等待客户呼叫");
                you = server.accept();
                System.out.println("客户的地址：" + you.getInetAddress());
            } catch (IOException e) {
                System.out.println("正在等待客户");
            }
            if (you != null) {
                ServerThread threadForClient = new ServerThread(you);
                threadForClient.start();
            }
        }
    }
}

class ServerThread extends Thread {
    static int id;
    static int name;
    static int password;
    static int email;
    static int birthday;
    Socket socket;
    DataOutputStream out = null;
    DataInputStream in = null;
    String s = null;

    ServerThread(Socket t) {
        socket = t;
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
        }
    }

    public void run() {
        //JDBC初始化
        // JDBC 驱动名及数据库 URL
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://localhost:3306/jf_data_dict?useSSL=false";
        // 数据库的用户名与密码，需要根据自己的设置
        final String USER = "root";
        final String PASS = "tantan";
        //jdbc
        Connection conn = null;
        Statement stmt = null;
        while (true) {
            try {
                double r = in.readDouble();

                // 注册 JDBC 驱动
                Class.forName(JDBC_DRIVER);
                // 打开链接
                System.out.println("连接数据库...");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                // 执行查询
                System.out.println(" 实例化Statement对象...");
                stmt = conn.createStatement();
                String sql;
                sql = "SELECT id,name,password,email,birthday FROM data";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.last()) {//只读取最后一个ID
                    // 通过字段检索
                    id = rs.getInt("id");
                    name = rs.getInt("name");
                    password = rs.getInt("password");
                    email = rs.getInt("email");
                    birthday = rs.getInt("birthday");
                }
                out.writeDouble(ServerThread.id);
                out.writeDouble(ServerThread.name);
                out.writeDouble(ServerThread.password);
                out.writeDouble(ServerThread.email);
                out.writeDouble(ServerThread.birthday);
            } catch (SQLException se) {
                // 处理 JDBC 错误
                se.printStackTrace();
            } catch (Exception e) {
                // 处理 Class.forName 错误
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}