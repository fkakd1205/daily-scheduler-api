package com.scheduler.daily_scheduler_api.exception;

public class CustomNotFoundDataException extends RuntimeException {

    public CustomNotFoundDataException(String message) {
        super(message);
    }

    public CustomNotFoundDataException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
