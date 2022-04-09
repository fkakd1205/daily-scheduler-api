package com.scheduler.daily_scheduler_api.domain.schedule.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.scheduler.daily_scheduler_api.domain.schedule.entity.ScheduleEntity;
import com.scheduler.daily_scheduler_api.domain.schedule.repository.ScheduleRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@WebAppConfiguration
@Transactional
public class ScheduleServiceTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Test
    @DisplayName("스케쥴 단일 생성")
    void 스케쥴_단일_생성() {
        // given
        UUID scheduleId = UUID.randomUUID();
        ScheduleEntity entity = ScheduleEntity.builder()
            .id(scheduleId)
            .content("할 일")
            .completed(false)
            .createdAt(LocalDateTime.now())
            .categoryId(UUID.fromString("79f7fe0d-6fb3-4b2b-8bfc-1b7402b63275"))
            .build();
    
        // when
        scheduleService.saveAndModify(entity);
        ScheduleEntity resultEntity = scheduleService.searchOne(scheduleId);

        // then
        Assertions.assertThat(entity.getId()).isEqualTo(resultEntity.getId());
        Assertions.assertThat(entity.getContent()).isEqualTo(resultEntity.getContent());
    }
}
