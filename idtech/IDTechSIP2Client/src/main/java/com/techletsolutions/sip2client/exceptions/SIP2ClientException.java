package com.techletsolutions.sip2client.exceptions;

import com.ceridwen.circulation.SIP.exceptions.ChecksumError;
import com.ceridwen.circulation.SIP.exceptions.ConnectionFailure;
import com.ceridwen.circulation.SIP.exceptions.MandatoryFieldOmitted;
import com.ceridwen.circulation.SIP.exceptions.MessageNotUnderstood;
import com.ceridwen.circulation.SIP.exceptions.RetriesExceeded;
import com.ceridwen.circulation.SIP.exceptions.SequenceError;
import com.techletsolutions.sip2client.error.Errors;

public class SIP2ClientException extends Exception {
 
    private static final long serialVersionUID = 1416841113916472161L;

    private Integer errorCode;
    
    private Throwable t;
    
    public SIP2ClientException() {
    	super();
    }
    
    public SIP2ClientException(Throwable t) {
    	super();
    	this.t = t;
    }

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	
	public int getError() {
		if (t != null) {
			if (t instanceof RetriesExceeded) {
				return Errors.ERROR_RETRIES_EXCEEDED;
			} else if ( t instanceof ConnectionFailure) {
				return Errors.ERROR_CONNECTION_FAILURE;
			} else if ( t instanceof MessageNotUnderstood) {
				return Errors.ERROR_MESSAGE_NOT_UNDERSTOOD;
			} else if ( t instanceof ChecksumError) {
				return Errors.ERROR_CHECKSUM_ERROR;
			} else if ( t instanceof SequenceError) {
				return Errors.ERROR_SEQUENCE_ERROR;
			} else if ( t instanceof MandatoryFieldOmitted) {
				return Errors.ERROR_MANDATORY_FIELD_OMITTED;
			} else {
				//TODO write handler here
			}
		}
		return Errors.UNKNOWN_ERROR;
	}
}
