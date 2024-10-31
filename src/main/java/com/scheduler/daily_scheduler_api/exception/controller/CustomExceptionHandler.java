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

       Message message = new Message();
       message.setStatus(HttpStatus.NOT_FOUND);
       message.setMessage("not_found");
       message.setMemo(e.getMessage());

       return new ResponseEntity<>(message, message.getStatus());
    }

    @ExceptionHandler({CustomInvalidDateFormatException.class})
    public ResponseEntity<?> customInvalidDateFormatExceptionHandler(CustomInvalidDateFormatException e) {
       log.error("EXCEPTION STACKTRACE => {}", e.getStackTrace());

       Message message = new Message();
       message.setStatus(HttpStatus.BAD_REQUEST);
       message.setMessage("invalid_date_format");
       message.setMemo(e.getMessage());

       return new ResponseEntity<>(message, message.getStatus());
    }

    @ExceptionHandler({CustomInvalidUserException.class})
    public ResponseEntity<?> customInvalidUserExceptionHandler(CustomInvalidUserException e) {
        log.error("EXCEPTION STACKTRACE => {}", e.getStackTrace());

        Message message = new Message();
        message.setStatus(HttpStatus.FORBIDDEN);
        message.setMessage("forbidden");
        message.setMemo(e.getMessage());

        return new ResponseEntity<>(message, message.getStatus());
    }
}
