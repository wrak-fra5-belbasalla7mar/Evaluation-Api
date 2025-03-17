package com.spring.evalapi.common.exception;

public class RatingNotFoundException extends RuntimeException {

    private static final String errorMessage = "Rating not found ";

    public RatingNotFoundException() {
        super(errorMessage);
    }
}