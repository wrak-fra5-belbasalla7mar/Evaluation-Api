package com.spring.evalapi.exception;

public class KpiAlreadyAssignedException extends RuntimeException {

    public KpiAlreadyAssignedException(String message) {
        super(message);
    }

}