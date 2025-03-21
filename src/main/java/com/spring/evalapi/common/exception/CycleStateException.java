package com.spring.evalapi.common.exception;

public class CycleStateException extends RuntimeException {
    public CycleStateException(String message) {
        super(message);
    }
    public CycleStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
