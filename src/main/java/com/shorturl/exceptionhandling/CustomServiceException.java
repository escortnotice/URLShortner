package com.shorturl.exceptionhandling;

public class CustomServiceException extends CustomBaseException {

	private static final long serialVersionUID = 1L;

	public CustomServiceException(String customMessage, Throwable e) {
		super(customMessage, e);
	}

}
