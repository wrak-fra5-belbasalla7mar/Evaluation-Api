package com.spring.evalapi.exception;

public class CycleNotOpenException extends RuntimeException {
    public CycleNotOpenException(String message) {
        super(message);
    }
}
