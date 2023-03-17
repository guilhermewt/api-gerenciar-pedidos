package com.webserviceproject.controllers.exceptions;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.webserviceproject.services.exceptions.BadRequestException;
import com.webserviceproject.services.exceptions.BadRequestExceptionDetails;
import com.webserviceproject.services.exceptions.ConflictException;
import com.webserviceproject.services.exceptions.ExceptionDetails;
import com.webserviceproject.services.exceptions.ValidationExceptionDetails;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionDetails> Error500(Exception ex) {
		return new ResponseEntity<ExceptionDetails>(BadRequestExceptionDetails.builder()
				.timestamp(Instant.now())
				.status(HttpStatus.BAD_REQUEST.value())				
				.error("Bad Request Exception, check the documentation")
				.details(ex.getMessage())
				.developerMessage(ex.getClass().getName())
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ExceptionDetails> nosuch(NoSuchElementException ex) {
		return new ResponseEntity<ExceptionDetails>(BadRequestExceptionDetails.builder()
				.timestamp(Instant.now())
				.status(HttpStatus.BAD_REQUEST.value())				
				.error("Bad Request Exception, check the documentation")
				.details(ex.getMessage())
				.developerMessage(ex.getClass().getName())
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ExceptionDetails> AuthenticationException(AuthenticationException ex) {
		return new ResponseEntity<ExceptionDetails>(BadRequestExceptionDetails.builder()
				.status(HttpStatus.FORBIDDEN.value())			
				.error("token invalid, expired or not present")
				.details(ex.getMessage())
				.developerMessage(ex.getClass().getName())
				.build(), HttpStatus.FORBIDDEN);
	}
	
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ExceptionDetails> resouceNotFound(BadRequestException bre){
		return new ResponseEntity<ExceptionDetails>(BadRequestExceptionDetails.builder()
				.timestamp(Instant.now())
				.status(HttpStatus.BAD_REQUEST.value())			
				.error("Bad Request Exception, check the documentation")
				.details(bre.getMessage())
				.developerMessage(bre.getClass().getName())
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<ExceptionDetails> conflictException(ConflictException ce){
		return new ResponseEntity<>(BadRequestExceptionDetails.builder()
				.timestamp(Instant.now())
				.status(HttpStatus.CONFLICT.value())
				.error("Conflict Exception, check the documentation")
				.details(ce.getMessage())
				.developerMessage(ce.getClass().getName())
				.build(), HttpStatus.CONFLICT);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining("'"));
		String fieldMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining("'"));
		
		return new ResponseEntity<>(ValidationExceptionDetails.builder()
				.timestamp(Instant.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.error("Bad Request exception, invalids field")
				.details(exception.getMessage())
				.developerMessage(exception.getClass().getName())
				.fields(fields)
				.fieldsMessage(fieldMessage)
				.build()
				, HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception exception, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ExceptionDetails exceptionDetails = ExceptionDetails.builder()
				.timestamp(Instant.now())
				.status(status.value())
				.error(exception.getCause().getMessage())
				.details(exception.getMessage())
				.developerMessage(exception.getClass().getName())
				.build();
		return new ResponseEntity<>(exceptionDetails, headers, status);
	}
	
}
