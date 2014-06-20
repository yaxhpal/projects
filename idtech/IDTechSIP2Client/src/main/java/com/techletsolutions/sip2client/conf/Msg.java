package com.techletsolutions.sip2client.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Msg {
	final static Logger logger = LoggerFactory.getLogger(Msg.class);

	private static final String CONFIGURATION_FILE = "IDTechSIP2Client.properties";

	private final static Properties prop = new Properties();

	public static String get(String key) {
		if (prop.isEmpty()) {
			loadProperties();
		}
		return prop.getProperty(key);
	}

	/*
	 * Load a properties file from class path, inside static method
	 */
	private static void loadProperties() {
		logger.debug("Entering into loadProperties");
		InputStream input = null;
		try {
			input = Msg.class.getClassLoader().getResourceAsStream(
					CONFIGURATION_FILE);
			if (input == null) {
				logger.error("Unable to find property file: " + CONFIGURATION_FILE);
				return;
			}
			prop.load(input);
		} catch (IOException ex) {
			logger.error("Unable to find property file: " + CONFIGURATION_FILE,
					ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error("Unknown error has occured.", e);
				}
			}
		}
	}

	public static int getIntProperty(String string) {
		return Integer.parseInt(get(string));
	}

	public static boolean getBoolProperty(String string) {
		return Boolean.parseBoolean(get(string));
	}
}
