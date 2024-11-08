package com.scheduler.daily_scheduler_api.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleSearchReqDto {
    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    LocalDateTime endDate;
}
