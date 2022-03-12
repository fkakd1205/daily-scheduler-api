package com.scheduler.daily_scheduler_api.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

import com.scheduler.daily_scheduler_api.domain.schedule.entity.ScheduleEntity;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Accessors(chain = true)
public class ScheduleDto {
    private UUID id;
    
    @Setter
    private String content;

    @Setter
    private Boolean completed;

    private LocalDateTime createdAt;

    @Setter
    private LocalDateTime updatedAt;

    @Setter
    private LocalDateTime completedAt;

    @Setter
    private UUID categoryId;

    public static ScheduleDto toDto(ScheduleEntity entity) {
        ScheduleDto dto = ScheduleDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .completed(entity.getCompleted())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .completedAt(entity.getCompletedAt())
                .categoryId(entity.getCategoryId())
                .build();

        return dto;
    }
}
