package com.techletsolutions.hulk.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class HulkConfig {
	private final static Properties prop = new Properties();

	public static String getProperty(String key) {
		if (prop.isEmpty()) {
			loadProperties();
		} 
		return prop.getProperty(key);
	}

	/**
	 * Load a properties file from class path, inside static method
	 * 
	 **/
	private static void loadProperties() {
		System.out.println("Entering into loadProperties");
		InputStream input = null;
		try {
			String filename = "hulkcnd.properties";
			input = HulkConfig.class.getClassLoader().getResourceAsStream(filename);
			if(input==null){
				return;
			}
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally{
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
