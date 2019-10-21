package com.jf.datadict.test;
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
 
/**
 * 读客户端的数据：socket中输入流，读，打印输出
 * 
 * @author Mr.Gao
 * 
 */
class ServerRead extends Thread {
	private Socket s;
 
	public ServerRead(Socket s) {
		this.s = s;
	}
 
	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String line = "";
			while (true) {
				line = br.readLine();
				if (!"".equals(line) && line != null) {
					System.out.println("客户端说：" + line);
				}
			}
		} catch (Exception e) {
 
		}
	}
}
 
/**
 * 向客户端写数据：socket中获取输出流，读键盘，写给客户端
 * 
 * @author Mr.Gao
 * 
 */
class ServerWrite extends Thread {
	private Socket socket;
 
	public ServerWrite(Socket socket) {
		this.socket = socket;
	}
 
	@Override
	public void run() {
		// 1.获取流
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			String str = "";
			// 2.循环向客户端写数据
			while (true) {
				str = input.readLine();
				bw.write(str);
				bw.newLine();
				bw.flush();
			}
		} catch (Exception e) {
 
		}
	}
}
 
public class ServersChat {
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(9090);
		System.out.println("服务器已经建立，，等待客户端连接。。");
		Socket socket = server.accept();
		System.out.println("已有客户端连接。。。");
		// 读，或者写
		// 启动线程，用于读客户端的数据
		new ServerRead(socket).start();
		// 启动线程，用于向客户端写数据
		new ServerWrite(socket).start();
	}
}