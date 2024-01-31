package com.scheduler.daily_scheduler_api.domain.schedule_category.controller;

import com.scheduler.daily_scheduler_api.domain.message.Message;
import com.scheduler.daily_scheduler_api.domain.schedule_category.service.ScheduleCategoryBusinessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/schedule-categories")
@RequiredArgsConstructor
public class ScheduleCategoryApiController {
    private final ScheduleCategoryBusinessService scheduleCategoryBusinessService;

    /**
     * <b>Search All Schedule Categories</b>
     * <p>
     * <b>GET : /api/v1/schedule-categories</b>
     * 
     * @return ResponseEntity
     * @see ScheduleCategoryBusinessService#searchAll
     */
    @GetMapping("/all")
    public ResponseEntity<?> searchAll() {
        Message message = Message.builder()
                .status(HttpStatus.OK)
                .data(scheduleCategoryBusinessService.searchAll())
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }
}
