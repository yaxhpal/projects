package com.techletsolutions.sip2client.error;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "PatronInformation")
//@XmlType(propOrder = { "code", "message" })
public class TechletPatronInformation {
	
	String patronStatus;
	String language;
	String transactionDate;
	Integer holdItemsCount;
	Integer overdueItemsCount;
//	charged items count
//	fine items count
//	recall items count
//	unavailable holds count
	
	
	private int code;
	private String message;

	@XmlElement(name = "code")
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	@XmlElement(name = "message")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "code="+code+" "+"message='"+message+"'.";
	}

}