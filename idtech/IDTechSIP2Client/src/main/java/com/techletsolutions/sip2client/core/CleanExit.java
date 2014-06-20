package com.techletsolutions.sip2client.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceridwen.circulation.SIP.transport.Connection;

public class CleanExit {

	final static Logger logger = LoggerFactory.getLogger(CleanExit.class);
	
	public static void exit(int exitCode, Connection conn) {
		releaseConnection(conn);
		System.exit(exitCode);
	}
	
	public static void releaseConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.disconnect();
			} catch (Exception e) {
				logger.debug("Could not close the connection", e);
			}
		}
	}
}
