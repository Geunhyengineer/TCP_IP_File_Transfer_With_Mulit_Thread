package main.java;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static main.java.ReadConfig.*;

public class FileReceiver extends LogService implements Runnable{
	
	Socket socket;

	DataInputStream dis;
	FileOutputStream fos;
	
	public FileReceiver(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		
		String copyPath = "";
		copyPath = copyDir();
		
		File copydir = new File(copyPath);
		if (!copydir.exists()) {
			copydir.mkdir();
			logger.info("폴더 생성 완료");
		}
		
		try {
			byte[] buffer = new byte[256];
			JSONParser parser = new JSONParser();
			JSONObject fileInfo;
			String fileName;
			long size;

			dis = new DataInputStream(socket.getInputStream());
			logger.info("파일 저장 시작...: "+Thread.currentThread().getName());
			
			dis.readFully(buffer);
			
			String strData = new String(buffer).trim();
			//logger.info("strData: " + strData);
			
			fileInfo = (JSONObject) parser.parse(strData);
			fileName = (String) fileInfo.get("fileName");
			size= ((Long) fileInfo.get("size")).longValue();
			//logger.info("FileReceiver 파일 이름:" + fileName);
			
			File file = new File(copyPath + "/" + fileName);
			//logger.info("Copy File: " +file.getPath());

			fos = new FileOutputStream(file);
			
			long data = 0;
			int length = -1;
			long totalRecv = 0;
			
			while ((length = dis.read(buffer)) != -1) {
				fos.write(buffer, 0, length);
				totalRecv = totalRecv + length;
				//logger.info("totalRecv: "+totalRecv + "length: "+length);
			}
			
			fos.flush();
			logger.info("파일 저장 완료...");

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
		    if (dis != null) try { dis.close(); } catch (IOException ignore) {}
		    if (fos != null) try { fos.close(); } catch (IOException ignore) {}
		    if (socket != null) try { socket.close(); } catch (IOException ignore) {}
		}
	}

}
