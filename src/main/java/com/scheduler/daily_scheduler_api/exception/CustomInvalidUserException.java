package com.scheduler.daily_scheduler_api.exception;

public class CustomInvalidUserException extends RuntimeException{
    public CustomInvalidUserException() {
    }

    public CustomInvalidUserException(String message) {
        super(message);
    }
}
