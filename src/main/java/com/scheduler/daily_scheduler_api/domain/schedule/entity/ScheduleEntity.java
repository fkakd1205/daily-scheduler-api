package com.scheduler.daily_scheduler_api.domain.schedule.entity;

import com.scheduler.daily_scheduler_api.domain.schedule.dto.ScheduleDto;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Accessors(chain = true)
@Table(name = "schedule")
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid")
    private Integer cid;

    @Type(type = "uuid-char")
    @Column(name = "id")
    private UUID id;

    @Column(name = "content")
    private String content;

    @Column(name = "completed")
    private Boolean completed;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Type(type = "uuid-char")
    @Column(name = "category_id")
    private UUID categoryId;

    /**
     * <b>Convert Related Method</b>
     * <p>
     * ScheduleDto => ScheduleEntity
     * 
     * @param dto : ScheduleDto
     * @return ScheduleEntity
     */
    public static ScheduleEntity toEntity(ScheduleDto dto) {
        ScheduleEntity entity = ScheduleEntity.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .completed(dto.getCompleted())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .completedAt(dto.getCompletedAt())
                .categoryId(dto.getCategoryId())
                .build();

        return entity;
    }
}
