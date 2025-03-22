package com.spring.evalapi.common.exception;

public class KpiNotFoundException extends RuntimeException {

    public KpiNotFoundException(String message) {
        super(message);
    }
}