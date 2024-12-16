package com.scheduler.daily_scheduler_api.exception.controller;

import com.scheduler.daily_scheduler_api.exception.CustomInvalidDateFormatException;
import com.scheduler.daily_scheduler_api.exception.CustomInvalidUserException;
import com.scheduler.daily_scheduler_api.exception.CustomNotFoundDataException;
import com.scheduler.daily_scheduler_api.domain.message.Message;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    
    @ExceptionHandler({CustomNotFoundDataException.class})
    public ResponseEntity<?> customNotFoundDataExceptionHandler(CustomNotFoundDataException e) {
       log.error("EXCEPTION STACKTRACE => {}", e.getStackTrace());

       Message message = Message.builder()
               .status(HttpStatus.NOT_FOUND)
               .message("not_found")
               .memo(e.getMessage())
               .build();

       return new ResponseEntity<>(message, message.getStatus());
    }

    @ExceptionHandler({CustomInvalidDateFormatException.class})
    public ResponseEntity<?> customInvalidDateFormatExceptionHandler(CustomInvalidDateFormatException e) {
       log.error("EXCEPTION STACKTRACE => {}", e.getStackTrace());

       Message message = Message.builder()
               .status(HttpStatus.BAD_REQUEST)
               .message("invalid_date_format")
               .memo(e.getMessage())
               .build();

       return new ResponseEntity<>(message, message.getStatus());
    }

    @ExceptionHandler({CustomInvalidUserException.class})
    public ResponseEntity<?> customInvalidUserExceptionHandler(CustomInvalidUserException e) {
        log.error("EXCEPTION STACKTRACE => {}", e.getStackTrace());

        Message message = Message.builder()
                .status(HttpStatus.FORBIDDEN)
                .message("forbidden")
                .memo(e.getMessage())
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }
}
