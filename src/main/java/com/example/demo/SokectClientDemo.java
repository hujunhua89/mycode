package com.example.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.tomcat.util.http.fileupload.IOUtils;

public class SokectClientDemo {
	
	public static void main(String[] args) {
		BufferedReader in = null;
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader myin = null;
		try {
			socket = new Socket("127.0.0.1", 10086);
			System.out.println("客户启动。。");
			in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(socket.getOutputStream());
			myin = new BufferedReader(new InputStreamReader(System.in));
			String inLine = myin.readLine();
			while (!"exit".equals(inLine)) {
				out.println(inLine);
				out.flush();
				System.out.println("Client:"+inLine);
				System.out.println("Server:"+in.readLine());
				inLine=myin.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			IOUtils.closeQuietly(myin);
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(socket);
		}
	}

}
