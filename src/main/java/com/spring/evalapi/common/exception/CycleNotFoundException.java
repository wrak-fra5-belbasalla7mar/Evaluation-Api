package com.spring.evalapi.common.exception;

public class CycleNotFoundException extends RuntimeException {

    public CycleNotFoundException(String message) {
        super(message);
    }
}