package com.scheduler.daily_scheduler_api.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ScheduleDtoForCompleted {
    private UUID id;

    @Setter
    private Boolean completed;
}
