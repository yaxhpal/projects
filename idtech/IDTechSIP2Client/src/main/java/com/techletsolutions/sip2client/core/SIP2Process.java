package com.techletsolutions.sip2client.core;

import com.ceridwen.circulation.SIP.messages.Message;
import com.ceridwen.circulation.SIP.transport.SocketConnection;

public class SIP2Process {
	
	protected SocketConnection connection;
	
	protected Message response;
	
	public SIP2Process() {}
	
	public SIP2Process(SocketConnection connection, Message message) {
		this.connection = connection;
		this.response = message;
	}
}
