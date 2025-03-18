package com.spring.evalapi.common.exception;

public class ObjectiveForUserNotFound extends RuntimeException {
    public ObjectiveForUserNotFound(String message) {
        super(message);
    }
    public ObjectiveForUserNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
