package com.scheduler.daily_scheduler_api.domain.schedule.service;

import com.scheduler.daily_scheduler_api.domain.schedule.dto.ScheduleDto;
import com.scheduler.daily_scheduler_api.domain.schedule.entity.ScheduleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ScheduleBusinessService {

    private ScheduleService scheduleService;

    @Autowired
    public ScheduleBusinessService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    public void create(ScheduleDto dto) {
        ScheduleEntity entity = ScheduleEntity.toEntity(dto);

        ScheduleEntity newEntity = ScheduleEntity.builder()
                .id(UUID.randomUUID())
                .content(entity.getContent())
                .completed(false)
                .createdAt(LocalDateTime.now())
                .categoryId(entity.getCategoryId())
                .build();

        scheduleService.saveAndModify(newEntity);
    }
}
