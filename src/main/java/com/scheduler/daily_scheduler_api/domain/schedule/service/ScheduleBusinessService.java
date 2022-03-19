package com.scheduler.daily_scheduler_api.domain.schedule.service;

import com.scheduler.daily_scheduler_api.domain.schedule.dto.ScheduleDto;
import com.scheduler.daily_scheduler_api.domain.schedule.entity.ScheduleEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleBusinessService {

    private final ScheduleService scheduleService;

    public void createOne(ScheduleDto dto) {
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
    
    public List<ScheduleDto> searchList() {
        List<ScheduleEntity> entities = scheduleService.searchList();
        List<ScheduleDto> dtos = entities.stream().map(r -> ScheduleDto.toDto(r)).collect(Collectors.toList());
        return dtos;
    }

    public List<ScheduleDto> searchListByDate(Map<String, Object> params) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        if (params.get("startDate") == null || params.get("endDate") == null) {
            return null;
        }

        startDate = LocalDateTime.parse(params.get("startDate").toString(), format);
        endDate = LocalDateTime.parse(params.get("endDate").toString(), format);

        List<ScheduleEntity> entities = scheduleService.searchListByDate(startDate, endDate);
        List<ScheduleDto> dtos = entities.stream().map(r -> ScheduleDto.toDto(r)).collect(Collectors.toList());
        return dtos;
    }

    public void deleteOne(UUID scheduleId) {
        scheduleService.deleteOne(scheduleId);
    }

    public void patchOne(ScheduleDto changedDto) {
        if(changedDto.getId() == null) {
            // TODO :: 커스텀 예외 생성하자
            throw new NullPointerException();
        }

        ScheduleEntity entity = scheduleService.searchOne(changedDto.getId());

        if(changedDto.getCategoryId() != null) {
            entity.setCategoryId(changedDto.getCategoryId());
        }else if(changedDto.getCompleted() != null) {
            entity.setCompleted(changedDto.getCompleted()).setCompletedAt(null);
        }else if(changedDto.getContent() != null) {
            entity.setContent(changedDto.getContent());
        }
        entity.setUpdatedAt(LocalDateTime.now());

        scheduleService.saveAndModify(entity);
    }

    public void updateBatch(List<ScheduleDto> dtos) {
        // 1. id값들로 스케쥴 엔티티 조회
        // 2. 엔티티와 dtos를 돌면서 동일한 id라면 entity값을 dtos로 변경
        // 3. (서비스단에 saveListAndModify 생성 후) saveListAndModify 실행
        List<UUID> idList = dtos.stream().map(r -> r.getId()).collect(Collectors.toList());
        List<ScheduleEntity> entities = scheduleService.searchAllById(idList);

        entities.stream().forEach(entity -> {
            dtos.stream().forEach(dto -> {
                if(dto.getId().equals(entity.getId())) {
                    entity.setCategoryId(dto.getCategoryId())
                        .setContent(dto.getContent())
                        .setUpdatedAt(LocalDateTime.now());

                    if(!entity.getCompleted() && dto.getCompleted()){
                        entity.setCompleted(dto.getCompleted()).setCompletedAt(LocalDateTime.now());
                    }
                }
            });
        });

        scheduleService.saveListAndModify(entities);
    }
}
