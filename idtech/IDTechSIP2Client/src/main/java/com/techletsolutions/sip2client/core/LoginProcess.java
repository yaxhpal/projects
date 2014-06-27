package com.techletsolutions.sip2client.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceridwen.circulation.SIP.messages.Login;
import com.ceridwen.circulation.SIP.messages.LoginResponse;
import com.ceridwen.circulation.SIP.messages.Message;
import com.ceridwen.circulation.SIP.transport.SocketConnection;
import com.techletsolutions.sip2client.conf.Msg;
import com.techletsolutions.sip2client.error.Errors;
import com.techletsolutions.sip2client.exceptions.SIP2ClientException;

/**
 * This class contains the methods to carry out login process in SelfCheck
 * client.
 * @author Yashpal Meena
 * @email yashpal@techletsolutions.com 
 *
 */
public class LoginProcess extends SIP2Process {

	final static Logger logger = LoggerFactory.getLogger(LoginProcess.class);

	private String loginUserId;
	
	private String loginPassword;
	
	public LoginProcess(SocketConnection connection, Message message, String loginUserId, String loginPassword) {
		super(connection, message);
		this.loginUserId = loginUserId;
		this.loginPassword = loginPassword;
	}

	@Override
	public int execute() {
		if (loginUserId == null) {
			logger.error(Msg.get("login.request.userid.invalid.error"));
			return Errors.ERROR_INVALID_LOGIN_REQUEST;
		}

		if (loginPassword == null) {
			logger.error(Msg.get("login.request.password.invalid.error"));
			return Errors.ERROR_INVALID_LOGIN_REQUEST;
		}

		Login loginRequest = new Login();
		loginRequest.setLoginUserId(loginUserId);
		loginRequest.setLoginPassword(loginPassword);
		try {
			response = connection.send(loginRequest);
		} catch (Exception e) {
			logger.error(Msg.get("login.connection.error"), e);
			return (new SIP2ClientException(e)).getError();
		} 
		if (!(response instanceof LoginResponse)) {
			logger.error(Msg.get("login.reponse.invalid.error"), response.toString());
			return Errors.ERROR_INVALID_LOGIN_REQUEST;
		} else {
			if (((LoginResponse) response).isOk()) {
				logger.info("Logged in successfully.");
			} else {
				logger.info(Msg.get("login.failed.error"),  response.toString());
				return Errors.ERROR_LOGIN_FAILED;
			}
		}
		return Errors.NO_ERROR;
	}
}
