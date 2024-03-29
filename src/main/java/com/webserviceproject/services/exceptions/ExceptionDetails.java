package com.webserviceproject.services.exceptions;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ExceptionDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Instant timestamp;
	protected Integer status;
	protected String error;
	protected String details;
	protected String developerMessage;
	
}
