package com.shorturl.exceptionhandling;

@SuppressWarnings("serial")
public class CustomServiceException extends CustomBaseException {

	public CustomServiceException(String customMessage) {
		super(customMessage);
	}

	public CustomServiceException(String customMessage, Throwable cause) {
		super(customMessage, cause);
	}

}
