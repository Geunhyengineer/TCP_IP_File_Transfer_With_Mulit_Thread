package main.java;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

public class FileNavi extends LogService{
	private static ExecutorService threadPool = Executors.newFixedThreadPool(3);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket socket;
		FileSender fileSender;
		String filePath = "C://make";
		File dir = new File(filePath);
	
		for (File file : dir.listFiles()) {
			if(file.isFile()) {
				socket =  new Socket();
				try {
					socket.connect(new InetSocketAddress("localhost", 8091));
					
					if(socket.isConnected()) {
						threadPool.submit(fileSender = new FileSender(socket, file));
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info("파일 이름: "+file.getName());
				logger.info("파일 크기: "+file.length());
				
			}
		}
	}
}
