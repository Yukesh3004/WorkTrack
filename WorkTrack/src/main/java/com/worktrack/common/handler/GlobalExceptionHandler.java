package com.worktrack.common.handler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.worktrack.common.exception.EmailAlreadyExistsException;
import com.worktrack.common.exception.InvalidCredentialsException;
import com.worktrack.common.response.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<?> hanndleInvalidCredentials(InvalidCredentialsException ex, HttpServletRequest request) {
		ErrorResponse error = ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.UNAUTHORIZED.value())
				.error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build();
		
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<?> handleEmailAlreadyExists(EmailAlreadyExistsException ex, HttpServletRequest request) {		
		ErrorResponse error = ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.error(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build();
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGeneralException(Exception ex,  HttpServletRequest request) {		
		ErrorResponse error = ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build();
		
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
		String errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.collect(Collectors.joining(", "));
		
		ErrorResponse error = ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.status()
	}

}
