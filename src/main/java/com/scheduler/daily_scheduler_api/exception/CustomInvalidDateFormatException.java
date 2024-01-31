package com.scheduler.daily_scheduler_api.exception;

public class CustomInvalidDateFormatException extends RuntimeException {

    public CustomInvalidDateFormatException(String message) {
        super(message);
    }

    public CustomInvalidDateFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
