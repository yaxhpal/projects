package com.techletsolutions.sip2client.core;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceridwen.circulation.SIP.messages.ACSStatus;
import com.ceridwen.circulation.SIP.messages.CheckOut;
import com.ceridwen.circulation.SIP.messages.CheckOutResponse;
import com.ceridwen.circulation.SIP.messages.Message;
import com.ceridwen.circulation.SIP.transport.SocketConnection;
import com.ceridwen.circulation.SIP.types.flagfields.SupportedMessages;
import com.techletsolutions.sip2client.conf.Msg;
import com.techletsolutions.sip2client.error.Errors;
import com.techletsolutions.sip2client.exceptions.SIP2ClientException;

/**
 * This contains methods to carry out checkout process in SelfCheck
 * Client.
 * @author Yashpal Meena
 * @email yashpal@techletsolutions.com
 *
 */
public class CheckoutProcess  extends SIP2Process {

	final static Logger logger = LoggerFactory.getLogger(CheckoutProcess.class);

	private String patronId;

	private String barcode;

	public CheckoutProcess(SocketConnection connection, Message message, String patronId, String barcode) {
		super(connection, message);
		this.patronId = patronId;
		this.barcode = barcode;
	}

	@Override
	public int execute() {

		if (patronId == null) {
			logger.error(Msg.get("checkout.request.patron.invalid.error"));
			return Errors.ERROR_INVALID_CHECKOUT_REQUEST;
		}

		if (barcode == null) {
			logger.error(Msg.get("checkout.request.item.invalid.error"));
			return Errors.ERROR_INVALID_CHECKOUT_REQUEST;
		}

		// Check if the server can support checkout
		if (!((ACSStatus) response).getSupportedMessages().isSet(SupportedMessages.CHECK_OUT)) {
			logger.error(Msg.get("checkout.notsupported.error"), response.toString());
			System.exit(Errors.ERROR_CHECKOUT_NOT_SUPPORTED);
		}

		// The code below would be the normal way of creating the request
		CheckOut checkOutRequest = new CheckOut();
		checkOutRequest.setPatronIdentifier(patronId);
		checkOutRequest.setItemIdentifier(barcode);
		checkOutRequest.setSCRenewalPolicy(Boolean.TRUE);
		checkOutRequest.setTransactionDate(new Date());
		try {
			response = connection.send(checkOutRequest);
		} catch (Exception e) {
			logger.error(Msg.get("error.checkout.connection"), e);
			return (new SIP2ClientException(e)).getError();
		} 

		if (!(response instanceof CheckOutResponse)) {
			logger.error(Msg.get("checkout.reponse.invalid.error"), response.toString());
			return Errors.ERROR_INVALID_CHECKOUT_REQUEST;
		} else {
			if (((CheckOutResponse) response).isOk()) {
				logger.info(Msg.get("checkout.success"), patronId, barcode);
			} else {
				logger.info(Msg.get("checkout.failed.error"),  ((CheckOutResponse) response).getScreenMessage());
				return Errors.ERROR_CHECKOUT_FAILED;
			}
		}
		return Errors.NO_ERROR;
	}
}
