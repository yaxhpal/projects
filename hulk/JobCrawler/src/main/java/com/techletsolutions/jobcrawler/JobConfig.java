package com.techletsolutions.jobcrawler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JobConfig {
	private final static Logger logger = LoggerFactory.getLogger(JobConfig.class);
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
			String filename = "jobcrawler.properties";
			input = JobConfig.class.getClassLoader().getResourceAsStream(filename);
			if(input==null){
				logger.error("Unable to find property file: " + filename);
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
