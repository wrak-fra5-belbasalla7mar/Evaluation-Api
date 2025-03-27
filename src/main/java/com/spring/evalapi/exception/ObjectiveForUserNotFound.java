package com.spring.evalapi.exception;

public class ObjectiveForUserNotFound extends RuntimeException {
    public ObjectiveForUserNotFound(String message) {
        super(message);
    }
}
