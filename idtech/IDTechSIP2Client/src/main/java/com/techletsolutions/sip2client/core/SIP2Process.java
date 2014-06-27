package com.techletsolutions.sip2client.core;

import com.ceridwen.circulation.SIP.messages.Message;
import com.ceridwen.circulation.SIP.transport.SocketConnection;
import com.techletsolutions.sip2client.error.Errors;

public class SIP2Process {
	
	protected SocketConnection connection;
	
	protected Message response;
	
	public SIP2Process() {}
	
	public SIP2Process(SocketConnection connection, Message message) {
		this.connection = connection;
		this.response = message;
	}
	
	public int execute() {
		return Errors.NO_ERROR;
	}
}
