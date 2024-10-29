package com.scheduler.daily_scheduler_api.exception;

public class CustomDuplicateIdException extends  RuntimeException {
    public CustomDuplicateIdException() {
        super();
    }

    public CustomDuplicateIdException(String message) {
        super(message);
    }
}
