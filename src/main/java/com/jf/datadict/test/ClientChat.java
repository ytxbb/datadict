package com.jf.datadict.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.*;

/**
 * 读取服务器的信息
 * 
 * @author Mr.Gao
 * 
 */
class ClientRead extends Thread {
	private Socket client;
 
	public ClientRead(Socket client) {
		this.client = client;
	}
 
	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			String line = "";
			while (true) {
				line = br.readLine();
				if (!"".equals(line) && line != null) {
					System.out.println("服务器说：" + line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
 
/**
 * 读键盘，写到服务器
 * @author Administrator
 */
class ClientWrite extends Thread {
	private Socket client;
 
	public ClientWrite(Socket client) {
		this.client = client;
	}
 
	@Override
	public void run() {
		BufferedReader input = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					client.getOutputStream()));
			String str = "";
			while (true) {
				str = input.readLine();
				bw.write(str);
				bw.newLine();
				bw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class ClientWrite2 extends Thread {
    Socket client;
    StringBuilder strbuilder = new StringBuilder();
    public ClientWrite2(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        //JDBC初始化
        // JDBC 驱动名及数据库 URL
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://10.2.48.95:3306/jf_dc_db?useSSL=false";
        // 数据库的用户名与密码，需要根据自己的设置
        final String USER = "root";
        final String PASS = "123456";
        //jdbc
        Connection conn = null;
        Statement stmt = null;

        // 注册 JDBC 驱动
        try {
            Class.forName(JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                if (stmt == null) {
                    System.out.println("跳过");
                    continue;
                }
                String sql = "SELECT uid,campus_name,parent_orgcode FROM p_campus";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.last()) {//只读取最后一个ID
                    // 通过字段检索
                    String uid = rs.getString("uid");
                    String campusName = rs.getString("campus_name");
                    String parentOrgcode = rs.getString("parent_orgcode");

                    strbuilder.append(uid).append("-").append(campusName).append("-").append(parentOrgcode);
                }
            } catch (SQLException se) {
                // 处理 JDBC 错误
                se.printStackTrace();
            } catch (Exception e) {
                // 处理 Class.forName 错误
                e.printStackTrace();
            }

            try {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                        client.getOutputStream()));
                while (true) {
                    bw.write(strbuilder.toString());
                    bw.newLine();
                    bw.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
 
public class ClientChat {
 
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Socket client = new Socket("127.0.0.1", 9090);
		System.out.println("客户端已经建立。。。");
		// 1.启动线程用于写给服务器
		new ClientRead(client).start();
		// 2.启动线程用于读取服务器的数据
        new ClientWrite2(client).start();
 
	}
 
}