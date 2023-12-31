package com.safnapp.notesapp.exception;

public class JwtExpiredException extends RuntimeException {
	public JwtExpiredException(String message) {
        super(message);
    }
}
