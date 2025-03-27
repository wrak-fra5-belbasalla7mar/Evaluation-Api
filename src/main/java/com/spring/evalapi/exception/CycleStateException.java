package com.spring.evalapi.exception;

public class CycleStateException extends RuntimeException {
    public CycleStateException(String message) {
        super(message);
    }
}
