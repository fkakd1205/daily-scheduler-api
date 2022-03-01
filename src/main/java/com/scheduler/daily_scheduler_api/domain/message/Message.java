package com.scheduler.daily_scheduler_api.domain.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private HttpStatus status;
    private Object data;
    private String message;
    private String memo;
}
