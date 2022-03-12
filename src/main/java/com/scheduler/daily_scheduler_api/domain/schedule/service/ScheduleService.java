package com.scheduler.daily_scheduler_api.domain.schedule.service;

import com.scheduler.daily_scheduler_api.domain.schedule.entity.ScheduleEntity;
import com.scheduler.daily_scheduler_api.domain.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

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
     * @param entity :List[ScheduleEntity]
     * @see ScheduleRepository#saveAll
     */
    public void saveListAndModify(List<ScheduleEntity> entities) {
        scheduleRepository.saveAll(entities);
    }

    /**
     * <b>DB Select Related Method</b>
     * <p>
     * schedule을 모두 조회한다.
     * 
     * @return List[ScheduleEntity]
     * @see ScheduleRepository#findAll
     */
    public List<ScheduleEntity> searchList() {
        return scheduleRepository.findAll();
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

        if(entityOpt.isPresent()) {
            return entityOpt.get();
        }else {
            throw new NullPointerException();
        }
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
     * @param scheduleId : UUID
     * @see ScheduleRepository#findById
     * @see ScheduleRepository#delete
     */
    public void deleteOne(UUID scheduleId) {
        scheduleRepository.findById(scheduleId).ifPresent(schedule -> {
            scheduleRepository.delete(schedule);
        });
    }
}
