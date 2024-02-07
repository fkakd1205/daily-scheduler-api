package com.scheduler.daily_scheduler_api.domain.schedule.dto;

import java.time.LocalDateTime;

import com.scheduler.daily_scheduler_api.domain.schedule.projection.ScheduleSummaryProjection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Accessors(chain = true)
public class ScheduleSummaryDto {
    private String datetime;
    private Integer completionCount;
    private Integer incompletionCount;

    public static ScheduleSummaryDto toDto(ScheduleSummaryProjection proj) {
        ScheduleSummaryDto dto = ScheduleSummaryDto.builder()
            .datetime(proj.getDatetime().toString())
            .completionCount(proj.getCompletionCount())
            .incompletionCount(proj.getIncompletionCount())
            .build();

        return dto;
    }
}
