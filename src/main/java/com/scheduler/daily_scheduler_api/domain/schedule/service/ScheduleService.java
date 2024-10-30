package com.scheduler.daily_scheduler_api.domain.schedule.service;

import com.scheduler.daily_scheduler_api.domain.schedule.entity.ScheduleEntity;
import com.scheduler.daily_scheduler_api.domain.schedule.projection.ScheduleSummaryProjection;
import com.scheduler.daily_scheduler_api.domain.schedule.repository.ScheduleRepository;
import com.scheduler.daily_scheduler_api.exception.CustomNotFoundDataException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    /**
     * <b>DB Create or Insert Related Method</b>
     * <p>
     * 단일 schedule을 등록 or 수정한다.
     * 
     * @param entity : ScheduleEntity
     * @see ScheduleRepository#save
     */
    public void saveAndModify(ScheduleEntity entity) {
        scheduleRepository.save(entity);
    }

    /**
     * <b>DB Create or Insert Related Method</b>
     * <p>
     * 다중 schedule을 등록 or 수정한다.
     * 
     * @param entities :List[ScheduleEntity]
     * @see ScheduleRepository#saveAll
     */
    public void saveListAndModify(List<ScheduleEntity> entities) {
        scheduleRepository.saveAll(entities);
    }

    /**
     * <b>DB Select Related Method</b>
     * <p>
     * id에 대응하는 schedule을 조회한다.
     * 
     * @return List[ScheduleEntity]
     * @see ScheduleRepository#findAll
     */
    public ScheduleEntity searchOne(UUID scheduleId) {
        Optional<ScheduleEntity> entityOpt = scheduleRepository.findById(scheduleId);

        return entityOpt.orElseThrow(() -> new CustomNotFoundDataException("데이터가 존재하지 않습니다."));
    }

    /**
     * <b>DB Select Related Method</b>
     * <p>
     * 해당 날짜의 schedule을 모두 조회한다.
     *
     * @param userId : Integer
     * @param startDate : LocalDateTime
     * @param endDate : LocalDateTime
     * @return List[ScheduleEntity]
     * @see ScheduleRepository#findAll
     */
    public List<ScheduleEntity> searchListByDate(Integer userId, LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleRepository.findAllByDate(userId, startDate, endDate);
    }

    /**
     * <b>DB Select Related Method</b>
     * <p>
     * id에 대응하는 schedule을 모두 조회한다.
     * 
     * @param idList : List[UUID]
     * @return List[ScheduleEntity]
     * @see ScheduleRepository#findAllById
     */
    public List<ScheduleEntity> searchAllById(List<UUID> idList) {
        return scheduleRepository.findAllById(idList);
    }

    /**
     * <b>DB Delete Related Method</b>
     * <p>
     * id에 대응하는 schedule을 제거한다.
     * 
     * @param entity : ScheduleEntity
     * @see ScheduleRepository#delete
     */
    public void deleteOne(ScheduleEntity entity) {
        scheduleRepository.delete(entity);
    }

    /**
     * <b>DB Select Related Method</b>
     * <p>
     * 해당 날짜 schedule의 전체 항목 수 / 완료 항목 수를 모두 조회한다.
     *
     * @param userId : Integer
     * @param startDate : LocalDateTime
     * @param endDate : LocalDateTime
     * @return List[ScheduleSummaryProjection]
     * @see ScheduleRepository#findSummaryByDate
     */
    public List<ScheduleSummaryProjection> searchSummaryByDate(Integer userId, LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleRepository.findSummaryByDate(userId, startDate, endDate);
    }
}
