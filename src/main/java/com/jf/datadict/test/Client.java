package com.jf.datadict.test;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * 原文链接：https://blog.csdn.net/qq1922631820/article/details/86405029
 */
public class Client {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        Socket mysocket = null;
        DataInputStream in = null;
        DataOutputStream out = null;
        Thread readData;
        Read read = null;
        try {
            mysocket = new Socket();
            read = new Read();
            readData = new Thread(read);
            System.out.print("输入服务器的IP：");
            String IP = scanner.nextLine();
            System.out.print("输入端口号：");
            int port = scanner.nextInt();
            InetAddress address = InetAddress.getByName(IP);
            InetSocketAddress socketAddress = new InetSocketAddress(address, port);
            mysocket.connect(socketAddress);
            in = new DataInputStream(mysocket.getInputStream());
            out = new DataOutputStream(mysocket.getOutputStream());
            read.setDataInputStream(in);
            readData.start();
        } catch (Exception e) {
            System.out.println("服务器已断开" + e);
        }
        System.out.println("接收数据:");
        while (true) {
            double radius = 0;
            try {
                out.writeDouble(radius);
            } catch (Exception e) {
            }
        }
    }
}

class Read implements Runnable {
    DataInputStream in;

    public void setDataInputStream(DataInputStream in) {
        this.in = in;
    }

    public void run() {
        double id = 0;
        double name = 0;
        double password = 0;
        double email = 0;
        double birthday = 0;
        while (true) {
            try {
                id = in.readDouble();
                System.out.println("id：" + id);
                name = in.readDouble();
                System.out.println("名字：" + name);
                password = in.readDouble();
                System.out.println("密码：" + password);
                email = in.readDouble();
                System.out.println("邮箱：" + email);
                birthday = in.readDouble();
                System.out.println("生日：" + birthday);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}