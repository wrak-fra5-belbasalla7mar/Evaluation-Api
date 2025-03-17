package com.spring.evalapi.common.exception;

public class KpiNotFoundException extends RuntimeException {

    private static final String errorMessage = "KPI not found ";

    public KpiNotFoundException() {
        super(errorMessage);
    }
}