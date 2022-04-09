package com.scheduler.daily_scheduler_api.exception.controller;

import com.scheduler.daily_scheduler_api.domain.message.Message;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler({DataAccessException.class})
    public ResponseEntity<?> dataAccessExceptionHandler(DataAccessException e) {
       log.warn("EXCEPTION STACKTRACE => {}", e.getStackTrace());
       log.warn("EXCEPTION ROOTCAUSE => {}", e.getRootCause());

       Message message = new Message();
       message.setStatus(HttpStatus.BAD_REQUEST);
       message.setMessage("db_error");
       message.setMemo("데이터베이스 오류. 관리자에게 문의하세요.");

       return new ResponseEntity<>(message, message.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> exceptionHandler(Exception e) {
        log.warn("EXCEPTION STACKTRACE => {}", e.getStackTrace());
 
        Message message = new Message();
        message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        message.setMessage("error");
        message.setMemo("알 수 없는 에러가 발생했습니다. 관리자에게 문의하세요.");
 
        return new ResponseEntity<>(message, message.getStatus());
     }
}
