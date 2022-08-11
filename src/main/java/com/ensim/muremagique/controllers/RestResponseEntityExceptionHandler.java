package com.ensim.muremagique.controllers;

import com.ensim.muremagique.services.BusinessException;
import com.ensim.muremagique.services.NotFoundException;
import com.ensim.muremagique.services.infrastructure.StorageException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler
	extends ResponseEntityExceptionHandler
{

	@ExceptionHandler(value
		= {BusinessException.class, StorageException.class})
	protected ResponseEntity<Object> businessException(RuntimeException ex, WebRequest request)
	{
		return handleExceptionInternal(ex, ex.getMessage(),
			new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value
		= {NotFoundException.class})
	protected ResponseEntity<Object> notFound(RuntimeException ex, WebRequest request)
	{
		return handleExceptionInternal(ex, ex.getMessage(),
			new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(value
		= {UsernameNotFoundException.class, ExpiredJwtException.class})
	protected ResponseEntity<Object> notAuthorized(RuntimeException ex, WebRequest request)
	{
		return handleExceptionInternal(ex, ex.getMessage(),
			new HttpHeaders(), HttpStatus.FORBIDDEN, request);
	}
}
