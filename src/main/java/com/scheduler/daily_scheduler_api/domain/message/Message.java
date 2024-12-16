package com.scheduler.daily_scheduler_api.domain.message;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private HttpStatus status;
    private Object data;
    private String message;
    private String memo;
}
