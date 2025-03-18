
package com.spring.evalapi.common.exception;

public class ObjectiveNotFoundException extends RuntimeException {

    public ObjectiveNotFoundException(String message) {
        super(message);
    }

    public ObjectiveNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}