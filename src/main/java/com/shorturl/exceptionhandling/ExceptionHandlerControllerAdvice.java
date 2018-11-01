package com.shorturl.exceptionhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
	
	Logger logger = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);
	
	public static final String DB_ERROR = "DB Issue Occured";
	
	/*
	 * For handling Database issues.
	 * 
	 * Spring Data JPA internally handles db exceptions and propagates the exception till the controller
	 * layer.
	 * The exception thrown by the framework is DataAccessException
	 */
	@ExceptionHandler(DataAccessException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public String handleException(DataAccessException e) {
		logger.error("Caught DataAccessException in ExceptionHandlerAdvice class:" , e);
		return DB_ERROR;
	}
}
