package com.safnapp.notesapp.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

@RestControllerAdvice
public class ApplicationExceptionHandler {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public Map<String, String> handleAuthenticationException(AuthenticationException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "Unauthorized");
        errorMap.put("message", ex.getMessage());
        errorMap.put("status", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        // You can add more details to the errorMap if needed

        return errorMap;
    }
	
	 @ResponseStatus(HttpStatus.BAD_REQUEST)
	 @ExceptionHandler(RuntimeException.class)
	    public Map<String, Object> handleResponseStatusException(RuntimeException ex) {
	        Map<String, Object> errorMap = new HashMap<>();
	        errorMap.put("error", "Bad Request");
	        errorMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
	        errorMap.put("message", ex.getMessage());
	        // You can add more details to the errorMap if needed

	        return errorMap;
	    }

	 @ExceptionHandler(Exception.class)
	    public Map<String, Object> handleException(Exception ex) {
	        Map<String, Object> errorMap = new HashMap<>();
	        errorMap.put("error", "Internal Server Error");
	        errorMap.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
	        errorMap.put("message", ex.getMessage());
	        // You can add more details to the errorMap if needed

	        return errorMap;
	    }
	 @ExceptionHandler(JwtExpiredException.class)
	    @ResponseStatus(HttpStatus.UNAUTHORIZED)
	    public ResponseEntity<String> handleJwtExpiredException(JwtExpiredException ex) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT has expired");
	    }
}
