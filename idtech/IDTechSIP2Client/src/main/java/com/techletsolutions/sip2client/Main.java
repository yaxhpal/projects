package com.techletsolutions.sip2client;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceridwen.circulation.SIP.messages.ACSStatus;
import com.ceridwen.circulation.SIP.messages.Message;
import com.ceridwen.circulation.SIP.messages.SCStatus;
import com.ceridwen.circulation.SIP.transport.SocketConnection;
import com.ceridwen.circulation.SIP.types.enumerations.ProtocolVersion;
import com.techletsolutions.sip2client.core.CheckinProcess;
import com.techletsolutions.sip2client.core.CheckoutProcess;
import com.techletsolutions.sip2client.core.LoginProcess;
import com.techletsolutions.sip2client.core.PatronInfoProcess;
import com.techletsolutions.sip2client.error.Errors;
import com.techletsolutions.sip2client.exceptions.SIP2ClientException;
import com.techletsolutions.sip2client.utils.JAXBUtil;

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
		System.setProperty("com.ceridwen.circulation.SIP.charset", "ISO8859_1");
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
			} else {
				getServerStatus(response);
			}
		}

		if (args.length == 0) {
			usages();
			logger.error("Invalid arguments. Command not understood.");
			System.err.println("Invalid arguments. Command not understood.");
			System.exit(Errors.ERROR_INVALID_ARGUMENTS);
		} else if (args[0].equalsIgnoreCase("login")) {
			LoginProcess loginProcess = new LoginProcess(connection, response,  args[1], args[2]);
			System.exit(loginProcess.execute());
		} else if (args[0].equalsIgnoreCase("checkout")) {
			CheckoutProcess ckout = new CheckoutProcess(connection, response,  args[1], args[2]);
			System.exit(ckout.execute());
			// checkOut(response, args[1], args[2]);
		} else if (args[0].equalsIgnoreCase("checkin")) {
			CheckinProcess ckin = new CheckinProcess(connection, response,  args[1]);
			System.exit(ckin.execute());
			//checkIn(response, args[1]);
		} else if (args[0].equalsIgnoreCase("info")) {
			PatronInfoProcess pInfo = new PatronInfoProcess(connection, response, args[1]);
			int code = pInfo.execute();
			try {
				JAXBUtil.print(pInfo.getPatronInfo(), System.out);
			} catch (JAXBException e) {
				System.out.println("Something wrong here");
				logger.error("Couldn't marshal the given object.", e);
			}
			System.exit(code);
		} else if (args[0].equalsIgnoreCase("iteminfo")) {
			//getItemInfo(response, args[1]);
		} else {
			logger.error("Invalid arguments. Command not understood {}.", Arrays.toString(args));
			System.err.println("Invalid arguments. Command not understood.");
			System.exit(Errors.ERROR_INVALID_ARGUMENTS);
		}
		// Close the connection 
		connection.disconnect();
	}

	private void getServerStatus(Message response) {
		String info = "DateTimeSync: " + (( ACSStatus) response).getDateTimeSync() + " | " +
				"InstId: " + (( ACSStatus) response).getInstitutionId() + " | " +
				"LibName: " + (( ACSStatus) response).getLibraryName() + " | " +
				"ProtVer: " + (( ACSStatus) response).getProtocolVersion() + " | " +
				"Retries: " + (( ACSStatus) response).getRetriesAllowed() + " | " +
				"SupMsgs: " + (( ACSStatus) response).getSupportedMessages() + " | " +
				"TermLoc: " + (( ACSStatus) response).getTerminalLocation() + " | " +
				"Timeout: " + (( ACSStatus) response).getTimeoutPeriod() + " | " +
				"PrtLine: " + (( ACSStatus) response).getPrintLine() + " | " +
				"SrcnMsg: " + (( ACSStatus) response).getScreenMessage() + " | " +
				"CheckIn: " + (( ACSStatus) response).isCheckInOk() + " | " +
				"CheckOut: " + (( ACSStatus) response).isCheckOutOk() + " | " +
				"Offline: " + (( ACSStatus) response).isOfflineOk() + " | " +
				"Online: " + (( ACSStatus) response).isOnlineStatus() + " | " +
				"Renewal: " + (( ACSStatus) response).isACSRenewalPolicy() + " | " +
				"StatusUpdate: " + (( ACSStatus) response).isStatusUpdateOk();
		logger.debug(info);
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
