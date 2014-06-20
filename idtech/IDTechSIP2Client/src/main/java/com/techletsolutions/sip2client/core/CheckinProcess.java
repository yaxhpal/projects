package com.techletsolutions.sip2client.core;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceridwen.circulation.SIP.messages.ACSStatus;
import com.ceridwen.circulation.SIP.messages.CheckIn;
import com.ceridwen.circulation.SIP.messages.CheckInResponse;
import com.ceridwen.circulation.SIP.messages.Message;
import com.ceridwen.circulation.SIP.transport.SocketConnection;
import com.ceridwen.circulation.SIP.types.flagfields.SupportedMessages;
import com.techletsolutions.sip2client.conf.Msg;
import com.techletsolutions.sip2client.error.Errors;
import com.techletsolutions.sip2client.exceptions.SIP2ClientException;

public class CheckinProcess extends SIP2Process {

	final static Logger logger = LoggerFactory.getLogger(CheckinProcess.class);
	
	private String barcodes;
	
	public CheckinProcess(SocketConnection connection, Message message, String barcodes) {
		super(connection, message);
		this.barcodes = barcodes;
	}
	
	public int execute() {

		if (barcodes == null) {
			logger.error(Msg.get("checkin.request.invalid.error"));
			return Errors.ERROR_INVALID_CHECKIN_REQUEST;
        }
		
		// Check if the server can support checkin
		if (!((ACSStatus) response).getSupportedMessages().isSet(SupportedMessages.CHECK_IN)) {
			logger.error(Msg.get("checkin.notsupported.error"), response.toString());
			return Errors.ERROR_CHECKIN_NOT_SUPPORTED;
		}
		
		// The code below would be the normal way of creating the request
		CheckIn checkInRequest = new CheckIn();
		checkInRequest.setItemIdentifier(barcodes);
		checkInRequest.setReturnDate(new Date());
		checkInRequest.setTransactionDate(new Date());
		
		try {
			response = connection.send(checkInRequest);
		} catch (Exception e) {
			logger.error(Msg.get("error.checkin.connection"), e);
			return (new SIP2ClientException(e)).getError();
		}
		if (!(response instanceof CheckInResponse)) {
			logger.error(Msg.get("checkin.reponse.invalid.error"), response.toString());
			return Errors.ERROR_INVALID_CHECKIN_REQUEST;
		} else {
			if (((CheckInResponse) response).isOk()) {
				logger.info(Msg.get("checkin.success"), barcodes);
			} else {
				logger.info(Msg.get("checkin.failed.error"),  ((CheckInResponse) response).getScreenMessage());
				logger.debug(((CheckInResponse) response).toString());
				return Errors.ERROR_CHECKIN_FAILED;
			}
		}
		return Errors.NO_ERROR;
	}
}
