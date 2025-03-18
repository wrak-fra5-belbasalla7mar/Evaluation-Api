package com.spring.evalapi.common.exception;

public class CycleNotOpenException extends RuntimeException {
    public CycleNotOpenException(String message) {
        super(message);
    }
  public CycleNotOpenException(String message, Throwable cause) {
    super(message, cause);
  }
}
