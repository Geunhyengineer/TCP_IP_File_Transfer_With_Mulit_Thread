package main.java;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

public class ServerListener extends LogService{
	private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket serverSocket;
		Socket socket = null;
		FileReceiver fileReceiver;
		boolean flag = false;
		
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress("localhost", 8091));
			logger.info("서버 준비 중...");
			
			while(!flag) {
			socket = serverSocket.accept(); // 클라이언트 요청 수락
			logger.info("접속 완료...");
			InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
			logger.info("접속 정보: "+isa);
			
			threadPool.submit(fileReceiver = new FileReceiver(socket));
			}
		} catch (IOException e) {
			e.printStackTrace();
			flag = true;
		}
	}

}
