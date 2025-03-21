package com.spring.evalapi.common.exception;

public class RatingNotFoundException extends RuntimeException {

    public RatingNotFoundException(String message) {
        super(message);
    }
    public RatingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}