package com.spring.evalapi.common.exception;

public class KpiAlreadyAssignedException extends RuntimeException {

    public KpiAlreadyAssignedException(String message) {
        super(message);
    }

    public KpiAlreadyAssignedException(String message, Throwable cause) {
        super(message, cause);
    }
}