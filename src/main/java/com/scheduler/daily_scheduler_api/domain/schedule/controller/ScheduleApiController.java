package com.scheduler.daily_scheduler_api.domain.schedule.controller;

import com.scheduler.daily_scheduler_api.domain.message.Message;
import com.scheduler.daily_scheduler_api.domain.schedule.dto.ScheduleDto;
import com.scheduler.daily_scheduler_api.domain.schedule.service.ScheduleBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleApiController {

    private ScheduleBusinessService scheduleBusinessService;

    @Autowired
    public ScheduleApiController(ScheduleBusinessService scheduleBusinessService) {
        this.scheduleBusinessService = scheduleBusinessService;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody ScheduleDto dto) {
        scheduleBusinessService.create(dto);

        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("")
    public ResponseEntity<?> searchList() {
        Message message = Message.builder()
                .status(HttpStatus.OK)
                .data(scheduleBusinessService.searchList())
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }
    
}
