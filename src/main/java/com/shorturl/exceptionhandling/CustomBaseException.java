package com.shorturl.exceptionhandling;

@SuppressWarnings("serial")
public class CustomBaseException extends Exception {

	public CustomBaseException(String customMessage) {
		super(customMessage);
	}

	public CustomBaseException(String customMessage, Throwable cause) {
		super(customMessage, cause);
	}

}
