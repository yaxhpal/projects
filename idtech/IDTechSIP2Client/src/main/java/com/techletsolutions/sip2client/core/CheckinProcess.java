package com.techletsolutions.sip2client.core;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceridwen.circulation.SIP.messages.ACSStatus;
import com.ceridwen.circulation.SIP.messages.CheckIn;
import com.ceridwen.circulation.SIP.messages.CheckInResponse;
import com.ceridwen.circulation.SIP.messages.Message;
import com.ceridwen.circulation.SIP.messages.PatronInformationResponse;
import com.ceridwen.circulation.SIP.transport.SocketConnection;
import com.ceridwen.circulation.SIP.types.flagfields.SupportedMessages;
import com.techletsolutions.sip2client.error.Errors;
import com.techletsolutions.sip2client.exceptions.SIP2ClientException;

public class CheckinProcess {

	final static Logger logger = LoggerFactory.getLogger(CheckinProcess.class);
	
	private SocketConnection connection;
	
	private Message response;
	
	private String borcodes;
	
	public CheckinProcess(SocketConnection connection, Message message, String barcodes) {
		this.connection = connection;
		this.response = message;
		this.borcodes = barcodes;
	}
	
	public void execute() {
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
			if (((CheckInResponse) response).isOk()) {
				logger.info("Checkin performed successfully.");
			} else {
				logger.info("Checkin couldn't be carried out.{}",  ((CheckInResponse) response).getScreenMessage());
				logger.debug(((CheckInResponse) response).toString());
			}
		}
		try {
			logger.debug("Patron name {}", ((PatronInformationResponse)response).getPersonalName());
			logger.debug("Patron email {}", ((PatronInformationResponse)response).getEmailAddress());
		} catch (Exception e) {
			logger.debug("Could not get patron information.");
		}
	}
}
