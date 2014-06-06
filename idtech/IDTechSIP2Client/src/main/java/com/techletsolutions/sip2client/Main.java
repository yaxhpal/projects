package com.techletsolutions.sip2client;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceridwen.circulation.SIP.messages.ACSStatus;
import com.ceridwen.circulation.SIP.messages.CheckIn;
import com.ceridwen.circulation.SIP.messages.CheckInResponse;
import com.ceridwen.circulation.SIP.messages.CheckOut;
import com.ceridwen.circulation.SIP.messages.CheckOutResponse;
import com.ceridwen.circulation.SIP.messages.Login;
import com.ceridwen.circulation.SIP.messages.LoginResponse;
import com.ceridwen.circulation.SIP.messages.Message;
import com.ceridwen.circulation.SIP.messages.PatronInformationResponse;
import com.ceridwen.circulation.SIP.messages.SCStatus;
import com.ceridwen.circulation.SIP.transport.SocketConnection;
import com.ceridwen.circulation.SIP.types.enumerations.ProtocolVersion;
import com.ceridwen.circulation.SIP.types.flagfields.SupportedMessages;
import com.techletsolutions.sip2client.error.Errors;
import com.techletsolutions.sip2client.exceptions.SIP2ClientException;

public class Main {

	final static Logger logger = LoggerFactory.getLogger(Main.class);

	private static String  HOST = "127.0.0.1";
	private static Integer PORT = 6001;
	final static Integer CONNECTION_TIMEOUT = 30000;
	final static Integer IDLE_TIMEOUT = 30000;
	final static Integer RETRY_ATTEMPTS = 2;
	final static Integer RETRY_WAIT = 500;
	private static SocketConnection connection;

	public Main() {
		logger.debug("Initializing SIP Client...");
		connection = new SocketConnection();
		connection.setHost(HOST);
		connection.setPort(PORT);
		connection.setConnectionTimeout(CONNECTION_TIMEOUT);
		connection.setIdleTimeout(IDLE_TIMEOUT);
		connection.setRetryAttempts(RETRY_ATTEMPTS);
		connection.setRetryWait(RETRY_WAIT);
		logger.debug("Connection: Host={}, Port={}, Connection Timeout={}", HOST, PORT, CONNECTION_TIMEOUT);
	}

	public static void main(String[] args) {
		// First initialize logger
		initializeLogger();
		if(args.length < 2) {
			logger.error("Please provide host address and port number. Wrong arguments: {}", Arrays.toString(args));
			System.err.println("Please provide host address and port number");
			System.exit(Errors.UNKNOWN_ERROR);
		} else {
			HOST = args[0].trim();
			try {
				PORT = Integer.parseInt(args[1].trim());
			} catch (NumberFormatException e) {
				logger.error("Invalid port number. Please provide integer value.", e);
				System.err.println("Invalid port number. Please provide integer value.");
				System.exit(Errors.ERROR_INVALID_PORT);
			}
			logger.debug("Connection: Host={}, Port={}, Connection Timeout={}", HOST, PORT, CONNECTION_TIMEOUT);
			Main executer = new Main();
			if (args.length > 2) {
				executer.execute(Arrays.copyOfRange(args, 2, args.length));
			} else {
				executer.usages();
				logger.error("Invalid arguments. Command not understood.");
				System.err.println("Invalid arguments. Command not understood.");
				System.exit(Errors.ERROR_INVALID_ARGUMENTS);
			}
		}
		logger.debug("Exiting... Perhaps no problems were encountered.");
		System.exit(Errors.NO_ERROR);
	}

	public void execute(String[] args) {
		// Establish connection first
		try {
			connection.connect();
			if (connection.isConnected()) {
				logger.info("Connected to SIP server successfully.");
			}
		} catch (Exception e) {
			logger.error("Error has been encountered during connection setup.", e);
			System.err.println("Error has been encountered during connection setup.");
			System.exit(Errors.ERROR_CONNECTION_FAILURE);
		}

		Message response = null;
		if (!args[0].equalsIgnoreCase("login")) {
			// It is necessary to send a SC Status with protocol version 2.0 else server will assume 1.0)
			SCStatus scStatusRequest = new SCStatus();
			scStatusRequest.setProtocolVersion(ProtocolVersion.VERSION_2_00);
			try {
				response = connection.send(scStatusRequest);
			} catch (Exception e) {
				logger.error("Error while sending protocol version to SIP2 Server {}", e);
				SIP2ClientException sip2Exception = new SIP2ClientException(e);
				System.err.println("Error while sending protocol version to SIP2 Server. Code: "+sip2Exception.getError());
				System.exit(sip2Exception.getError());
			}
			if (!(response instanceof ACSStatus)) {
				logger.error("Error - Status Request did not return valid response from server.");
				System.err.println("Error - Status Request did not return valid response from server.");
				System.exit(Errors.ERROR_STATUS_REQUEST);
			}
		}
		
		if (args.length == 0) {
			usages();
			logger.error("Invalid arguments. Command not understood.");
			System.err.println("Invalid arguments. Command not understood.");
			System.exit(Errors.ERROR_INVALID_ARGUMENTS);
		} else if (args[0].equalsIgnoreCase("login")) {
			login(response, args[1], args[2]);
		} else if (args[0].equalsIgnoreCase("checkout")) {
			checkOut(response, args[1], args[2]);
		} else if (args[0].equalsIgnoreCase("checkin")) {
			checkIn(response, args[1]);
		} else {
			logger.error("Invalid arguments. Command not understood {}.", Arrays.toString(args));
			System.err.println("Invalid arguments. Command not understood.");
			System.exit(Errors.ERROR_INVALID_ARGUMENTS);
		}
		// Close the connection 
		connection.disconnect();
	}

	public void login(Message response, String loginUserId, String loginPassword) {
		Login loginRequest = new Login();
		loginRequest.setLoginUserId(loginUserId);
		loginRequest.setLoginPassword(loginPassword);
		try {
			response = connection.send(loginRequest);
		} catch (Exception e) {
			logger.error("Error while processing login request {}", e);
			SIP2ClientException sip2Exception = new SIP2ClientException(e);
			System.err.println("Error while processing login request. code: "+sip2Exception.getError());
			System.exit(sip2Exception.getError());
		} 
		if (!(response instanceof LoginResponse)) {
			logger.error("Error - Login Request did not return valid response from server {}", response.toString());
			System.err.println("Error - Login Request did not return valid response from server");
			System.exit(Errors.ERROR_INVALID_LOGIN_REQUEST);
		} else {
			if (((LoginResponse) response).isOk()) {
				logger.info("Logged in successfully.");
				//System.out.println("Logged in successfully.");
			} else {
				logger.error("Failed to login. There was unknown error.", response.toString());
				System.err.println("Failed to login. There was unknown error.");
				System.exit(Errors.ERROR_INVALID_LOGIN_REQUEST);
			}
		}
	}

	public void checkOut(Message response, String patronId, String borcodes) {
		// Check if the server can support checkout
		if (!((ACSStatus) response).getSupportedMessages().isSet(SupportedMessages.CHECK_OUT)) {
			logger.error("Check out not supported {}", response.toString());
			System.err.println("Check out not supported");
			System.exit(Errors.ERROR_CHECKOUT_NOT_SUPPORTED);
		}

		// The code below would be the normal way of creating the request
		CheckOut checkOutRequest = new CheckOut();
		checkOutRequest.setPatronIdentifier(patronId);
		checkOutRequest.setItemIdentifier(borcodes);
		checkOutRequest.setSCRenewalPolicy(Boolean.TRUE);
		checkOutRequest.setTransactionDate(new Date());
		try {
			response = connection.send(checkOutRequest);
		} catch (Exception e) {
			logger.error("Error while processing checkout request {}", e);
			SIP2ClientException sip2Exception = new SIP2ClientException(e);
			System.err.println("Error while processing checkout request. code: "+sip2Exception.getError());
			System.exit(sip2Exception.getError());
		} 

		if (!(response instanceof CheckOutResponse)) {
			logger.error("Error - CheckOut Request did not return valid response from server {}", response.toString());
			System.err.println("Error - CheckOut Request did not return valid response from server.");
			System.exit(Errors.ERROR_INVALID_CHECKOUT_REQUEST);
		} else {
			logger.debug( ((CheckOutResponse) response).getScreenMessage());
			logger.info(((CheckOutResponse) response).getScreenMessage());
			//System.out.println( ((CheckOutResponse) response).getScreenMessage());
		}
		try {
			logger.debug("Patron name {}", ((PatronInformationResponse)response).getPersonalName());
			logger.debug("Patron email {}", ((PatronInformationResponse)response).getEmailAddress());
		} catch (Exception e) {
			logger.debug("Could not get patron information.");
		}
	}

	public void checkIn(Message response, String borcodes) {
		// Check if the server can support checkin
		if (!((ACSStatus) response).getSupportedMessages().isSet(SupportedMessages.CHECK_IN)) {
			logger.error("Check out not supported {}", response.toString());
			System.err.println("Checkin not supported");
			System.exit(Errors.ERROR_CHECKIN_NOT_SUPPORTED);
		}
		
		// The code below would be the normal way of creating the request
		CheckIn checkInRequest = new CheckIn();
		checkInRequest.setItemIdentifier(borcodes);
		checkInRequest.setReturnDate(new Date());
		checkInRequest.setTransactionDate(new Date());
		try {
			response = connection.send(checkInRequest);
		} catch (Exception e) {
			logger.error("Error while processing checkin request {}", e);
			SIP2ClientException sip2Exception = new SIP2ClientException(e);
			System.err.println("Error while processing checkin request. code: "+sip2Exception.getError());
			System.exit(sip2Exception.getError());
			
		}
		if (!(response instanceof CheckInResponse)) {
			logger.error("Error - CheckIn Request did not return valid response from server {}", response.toString());
			System.err.println("Error - CheckIn Request did not return valid response from server");
			System.exit(Errors.ERROR_INVALID_CHECKIN_REQUEST);
		} else {
			logger.debug( ((CheckInResponse) response).getScreenMessage());
			System.out.println( ((CheckInResponse) response).getScreenMessage());
		}
		try {
			logger.debug("Patron name {}", ((PatronInformationResponse)response).getPersonalName());
			logger.debug("Patron email {}", ((PatronInformationResponse)response).getEmailAddress());
		} catch (Exception e) {
			logger.debug("Could not get patron information.");
		}
	}

	public static void initializeLogger() {
		org.apache.log4j.Logger rootLogger = org.apache.log4j.Logger.getRootLogger();
		rootLogger.setLevel(Level.DEBUG);
		String logginDir = System.getProperty("java.io.tmpdir")+File.separator+"SIP2Client";
		logger.debug("logging directory is: {}.", logginDir);
		File logDir = new File(logginDir);
		boolean isloggingDirectoryCreated = false;
		if (!logDir.exists()) {
			isloggingDirectoryCreated = new File(logginDir).mkdir();
		} else {
			isloggingDirectoryCreated = true;
		}
		if (isloggingDirectoryCreated) {
			try {
				//Define log pattern layout
				PatternLayout layout = new PatternLayout("%d{ISO8601} [%t] %-5p %c %x - %m%n");
				//Define file appender with layout and output log file name
				RollingFileAppender fileAppender = new RollingFileAppender(layout, logginDir+File.separator+"IDTechSIP2Client.log");
				fileAppender.setMaxFileSize("1MB");
				fileAppender.setMaxBackupIndex(1);
				//Add the appender to root logger
				rootLogger.addAppender(fileAppender);
			} catch (IOException e) {
				logger.error("Couldn't initialize logger", e);
				System.exit(Errors.ERROR_LOGGER_COULD_NOT_BE_INITIALIZED);
			}
		} else {
			logger.error("Failed to create directory {}", logginDir);
			System.exit(Errors.ERROR_LOGGER_COULD_NOT_BE_INITIALIZED);
		}
	}
	
	private void usages() {
		logger.info("Usages: <command> arguments");
		System.out.println();
		logger.info("Example: ");
		logger.info("login <username> <password>");
		logger.info("checkout <patronId> <itemId>");
		logger.info("checkin <itemId>");
	}
}
