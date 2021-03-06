package com.techletsolutions.sip2client.error;

public class Errors {

	public static final int UNKNOWN_ERROR = -1;
	
	public final static int NO_ERROR = 0;
	
	public final static int ERROR_CONNECTION_FAILURE = 1;
	
	public final static int ERROR_RETRIES_EXCEEDED = 2;
	
	public final static int ERROR_MESSAGE_NOT_UNDERSTOOD = 3;
	
	public final static int ERROR_CHECKSUM_ERROR = 4;
	
	public final static int ERROR_SEQUENCE_ERROR = 5;
	
	public final static int ERROR_MANDATORY_FIELD_OMITTED = 6;
	
	public final static int ERROR_INVALID_FIELD_LENGTH = 7;
	
	public final static int ERROR_STATUS_REQUEST = 8;
	
	public final static int ERROR_CHECKOUT_NOT_SUPPORTED = 9;

	public static final int ERROR_INVALID_CHECKOUT_REQUEST = 10;

	public static final int ERROR_INVALID_CHECKIN_REQUEST =11;

	public static final int ERROR_INVALID_LOGIN_REQUEST = 12;

	public static final int ERROR_INVALID_ARGUMENTS = 13;

	public static final int ERROR_INVALID_PORT = 14;

	public static final int ERROR_LOGGER_COULD_NOT_BE_INITIALIZED = 15;

	public static final int ERROR_CHECKIN_NOT_SUPPORTED = 16;

	public static final int ERROR_PATRON_INFORMATION_SUPPORTED = 17;

	public static final int ERROR_INVALID_PATRON_INFORMATION_REQUEST = 18;

	public static final int ERROR_ITEM_INFORMATION_SUPPORTED = 19;

	public static final int ERROR_CHECKIN_FAILED = 20;

	public static final int ERROR_CHECKOUT_FAILED = 21;

	public static final int ERROR_LOGIN_FAILED = 21;

	public static final int ERROR_INVALID_ITEM_INFORMATION_REQUEST = 22;

	public static final int ERROR_INVALID_PATRON = 23;
}
