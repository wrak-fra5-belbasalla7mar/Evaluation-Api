package com.spring.evalapi.exception;

public class FieldIsRequiredException extends RuntimeException {
    public FieldIsRequiredException(String message) {
        super(message);
    }
}
