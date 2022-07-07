package main.java;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

public class FileSender extends LogService implements Runnable {

	Socket socket;
	File file;
	
	DataOutputStream dos;
	FileInputStream fis;


	public FileSender(Socket socket, File file) {
		// TODO Auto-generated constructor stub
		super();
		this.socket = socket;
		this.file = file;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		byte[] buffer = new byte[256];
		JSONObject jsonData = new JSONObject();
		int length = -1;


		jsonData.put("fileName", file.getName());
		jsonData.put("size", file.length());
		
		logger.info("FileSender 파일 이름: " + file.getName());

		String json = jsonData.toString();
		byte[] jsonBytes = json.getBytes();

		// byte 배열 복사 - jsonBytes를 0부터 buffer에 넣는데, 0~jsonBytes 길이만큼
		System.arraycopy(jsonBytes, 0, buffer, 0, jsonBytes.length);

		logger.info("파일 전송 시작...: "+Thread.currentThread().getName());
		
		try {

			dos = new DataOutputStream(socket.getOutputStream());
			dos.write(buffer);
			dos.flush();

			fis = new FileInputStream(file);
			while ((length = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, length);
			}
			
			dos.flush();
			logger.info("파일 전송 완료: "+Thread.currentThread().getName());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    if (dos != null) try { dos.close(); } catch (IOException ignore) {}
		    if (fis != null) try { fis.close(); } catch (IOException ignore) {}
		    if (socket != null) try { socket.close(); } catch (IOException ignore) {}
		}

	}

}
