package main.java;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import static main.java.ReadConfig.*;

public class CreateFile extends LogService{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String makePath = "";
		makePath = makeDir();
		System.out.println("dir"+makePath);
		File makedir = new File(makePath);

		if (!makedir.exists()) {
			makedir.mkdir();
		}

		for (int i = 1; i <= 60; i++) {
			File newFile = new File(makePath+"/file"+ i);

			try {
				newFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} // end for

		logger.info("파일 생성 완료");
	}

}