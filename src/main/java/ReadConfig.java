package main.java;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ReadConfig {
	Properties prop = new Properties();
	static String fileName = ReadConfig.class.getResoruce("/resource/config.properties").getPath();

	public static String makeDir() {
		String makeDir = "";

		try {
			System.out.println("fileName: "+fileName);
//			prop.load(new FileReader("C:\\workSpace\\clientServerFileCopy\\src\\main\\resource\\config.properties"));
			prop.load(new FileReader(fileName));
			makeDir = prop.getProperty("makeDir");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return makeDir;
	}
	
	public static String copyDir() {
		Properties prop = new Properties();
		String copyDir = "";
		try {
			prop.load(new FileReader("C:\\workSpace\\clientServerFileCopy\\src\\main\\resource\\config.properties"));
			copyDir = prop.getProperty("copyDir");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return copyDir;
	}
}