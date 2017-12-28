package com.shorturl.exceptionhandling;

public class CustomBaseException extends Exception {

	private static final long serialVersionUID = 1L;

	public CustomBaseException(String customMessage, Throwable e) {
		super(customMessage, e);
	}
	
}
