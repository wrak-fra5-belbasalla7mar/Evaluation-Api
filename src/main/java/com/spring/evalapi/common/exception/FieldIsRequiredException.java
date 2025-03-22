package com.spring.evalapi.common.exception;

public class FieldIsRequiredException extends RuntimeException {
    public FieldIsRequiredException(String message) {
        super(message);
    }
}
