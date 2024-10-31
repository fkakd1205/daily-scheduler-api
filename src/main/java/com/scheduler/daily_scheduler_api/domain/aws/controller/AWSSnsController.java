package com.scheduler.daily_scheduler_api.domain.aws.controller;

import com.scheduler.daily_scheduler_api.domain.aws.service.AWSSnsService;
import com.scheduler.daily_scheduler_api.domain.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notify")
public class AWSSnsController {
    private final AWSSnsService snsService;

    @PostMapping("/create-topic")
    public ResponseEntity<?> createTopic(@RequestParam("topicName") String topicName) {
        snsService.createTopic(topicName);

        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestParam("endPoint") String endPoint, @RequestParam("topicArn") String topicArn) {
        snsService.subscribe(endPoint, topicArn);

        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }

    @PostMapping("/publish")
    public ResponseEntity<?> publish(@RequestParam("topicArn") String topicArn, @RequestBody Map<String, Object> pubMessage) {
        Message message = Message.builder()
                .status(HttpStatus.OK)
                .data(snsService.publish(topicArn, pubMessage))
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }
}
