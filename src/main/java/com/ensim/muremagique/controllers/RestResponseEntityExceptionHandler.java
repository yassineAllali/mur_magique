package com.ensim.muremagique.controllers;

import java.util.HashMap;
import java.util.Map;

import com.ensim.muremagique.services.BusinessException;
import com.ensim.muremagique.services.NotFoundException;
import com.ensim.muremagique.services.infrastructure.StorageException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler
{

	@ExceptionHandler(value
		= {BusinessException.class, StorageException.class})
	protected ResponseEntity<Object> businessException(RuntimeException ex)
	{
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value
		= {NotFoundException.class})
	protected ResponseEntity<Object> notFound(RuntimeException ex)
	{
		Map<String, String> payload = new HashMap<>();
		payload.put("message", ex.getMessage());
		return new ResponseEntity<>(payload, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value
		= {UsernameNotFoundException.class, ExpiredJwtException.class})
	protected ResponseEntity<Object> notAuthorized(RuntimeException ex)
	{
		System.out.println(ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}
}
