package com.github.lucasyukio.nossobancodigital.advice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		final List<String> errors = new ArrayList<String>();
		
		for (final FieldError error : ex.getBindingResult().getFieldErrors())
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors())
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		
		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errors);
		
		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		final String error = "O corpo da requisição não pode ser nulo";
        
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error);
		
		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}

}
