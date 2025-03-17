package com.spring.evalapi.common.exception;

import com.spring.evalapi.entity.Cycle;

public class CycleNotFoundException extends RuntimeException{
    private static final String errorMessage = "Cycle not found ";

    public CycleNotFoundException() {
        super(errorMessage);
    }
}
