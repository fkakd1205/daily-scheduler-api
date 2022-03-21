package com.scheduler.daily_scheduler_api.domain.schedule_category.dto;

import com.scheduler.daily_scheduler_api.domain.schedule_category.entity.ScheduleCategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Accessors(chain = true)
public class ScheduleCategoryDto {
    private UUID id;
    private String name;
    private String description;

    /**
     * <b>Convert Related Method</b>
     * <p>
     * ScheduleCategoryEntity => ScheduleCategoryDto
     * 
     * @param entity : ScheduleCategoryEntity
     * @return ScheduleCategoryDto
     */
    public static ScheduleCategoryDto toDto(ScheduleCategoryEntity entity) {
        ScheduleCategoryDto dto = ScheduleCategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();

        return dto;
    }
}
