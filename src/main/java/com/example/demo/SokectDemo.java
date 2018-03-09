package com.example.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.tomcat.util.http.fileupload.IOUtils;

public class SokectDemo {
	
	public static void main(String[] args) {
		ServerSocket server = null;
		BufferedReader in = null;
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader myin = null;
		try {
			server = new ServerSocket(10086);
			System.out.println("启动服务器。。监听中");
			socket = server.accept();
			System.out.println("监听来源："+socket.getLocalAddress().getHostAddress());
			in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(socket.getOutputStream());
			myin = new BufferedReader(new InputStreamReader(System.in));
			String inLine = myin.readLine();
			while (!"exit".equals(inLine)) {
				out.println(inLine);
				out.flush();
				System.out.println("Server:"+inLine);
				System.out.println("Client:"+in.readLine());
				inLine=myin.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			IOUtils.closeQuietly(myin);
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(socket);
			IOUtils.closeQuietly(server);
		}
	}

}
