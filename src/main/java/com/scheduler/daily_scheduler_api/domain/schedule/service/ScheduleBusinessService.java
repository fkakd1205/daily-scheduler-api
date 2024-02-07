package com.scheduler.daily_scheduler_api.domain.schedule.service;

import com.scheduler.daily_scheduler_api.domain.schedule.dto.ScheduleDto;
import com.scheduler.daily_scheduler_api.domain.schedule.dto.ScheduleDtoForCompleted;
import com.scheduler.daily_scheduler_api.domain.schedule.entity.ScheduleEntity;
import com.scheduler.daily_scheduler_api.exception.CustomInvalidDateFormatException;
import com.scheduler.daily_scheduler_api.exception.CustomNotFoundDataException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * <b>DB Insert Related Method</b>
     * <p>
     * schedule을 생성한다.
     * 
     * @param dto : ScheduleDto
     * @see ScheduleEntity#toEntity
     * @see ScheduleService#saveAndModify
     */
    @Transactional
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
    
    /**
     * <b>DB Select Related Method</b>
     * <p>
     * schedule을 모두 조회한다.
     * 
     * @param dto : ScheduleDto
     * @see ScheduleService#searchList
     * @see ScheduleDto#toDto
     */
    // public List<ScheduleDto> searchList() {
    //     List<ScheduleEntity> entities = scheduleService.searchList();
    //     List<ScheduleDto> dtos = entities.stream().map(r -> ScheduleDto.toDto(r)).collect(Collectors.toList());
    //     return dtos;
    // }

    /**
     * <b>DB Select Related Method</b>
     * <p>
     * 선택된 날짜 범위의 schedule을 모두 조회한다.
     * 
     * @param params : Map[String, Object]
     * @see ScheduleService#searchListByDate
     * @see ScheduleDto#toDto
     */
    @Transactional(readOnly = true)
    public List<ScheduleDto> searchListByDate(Map<String, Object> params) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        Object startDate = params.get("startDate");
        Object endDate = params.get("endDate");

        if (startDate == null || endDate == null) {
            throw new CustomInvalidDateFormatException("검색된 날짜 형식이 올바르지 않습니다.");
        }

        LocalDateTime start = LocalDateTime.parse(startDate.toString(), format);
        LocalDateTime end = LocalDateTime.parse(endDate.toString(), format);

        List<ScheduleEntity> entities = scheduleService.searchListByDate(start, end);
        List<ScheduleDto> dtos = entities.stream().map(r -> ScheduleDto.toDto(r)).collect(Collectors.toList());
        return dtos;
    }

    /**
     * <b>DB Delete Related Method</b>
     * <p>
     * scheduleId에 대응되는 schedule을 제거한다.
     * 
     * @param scheduleId : UUID
     * @see ScheduleService#searchOne
     * @see ScheduleService#deleteOne
     */
    @Transactional
    public void deleteOne(UUID scheduleId) {
        ScheduleEntity entity = scheduleService.searchOne(scheduleId);
        scheduleService.deleteOne(entity);
    }

    /**
     * <b>DB Update Related Method</b>
     * <p>
     * schedule의 완료 여부를 업데이트한다.
     * 
     * @param dto : ScheduleDtoForCompleted
     * @see ScheduleService#searchOne
     * @see ScheduleService#saveAndModify
     */
    @Transactional
    public void updateCompeletedSchedule(ScheduleDtoForCompleted dto) {
        UUID scheduleId = dto.getId();
        if(scheduleId == null) {
            throw new CustomNotFoundDataException("수정 데이터가 존재하지 않습니다.");
        }

        ScheduleEntity entity = scheduleService.searchOne(scheduleId);

        entity.setCompleted(dto.getCompleted())
            .setUpdatedAt(LocalDateTime.now());

        if(dto.getCompleted()) {
            entity.setCompletedAt(LocalDateTime.now());
        }else {
            entity.setCompletedAt(null);
        }

        scheduleService.saveAndModify(entity);
    }

    /**
     * <b>DB Update Related Method</b>
     * <p>
     * 다중 schedule을 수정한다.
     * 
     * @param dtos : List[ScheduleDto]
     * @see ScheduleService#searchAllById
     * @see ScheduleService#saveListAndModify
     */
    @Transactional
    public void updateBatch(List<ScheduleDto> dtos) {
        List<UUID> idList = dtos.stream().map(r -> r.getId()).collect(Collectors.toList());
        List<ScheduleEntity> entities = scheduleService.searchAllById(idList);

        entities.stream().forEach(entity -> {
            dtos.stream().forEach(dto -> {
                if(dto.getId().equals(entity.getId())) {
                    entity.setContent(dto.getContent())
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
