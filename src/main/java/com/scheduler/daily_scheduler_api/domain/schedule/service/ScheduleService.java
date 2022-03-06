package com.scheduler.daily_scheduler_api.domain.schedule.service;

import com.scheduler.daily_scheduler_api.domain.schedule.entity.ScheduleEntity;
import com.scheduler.daily_scheduler_api.domain.schedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public void saveAndModify(ScheduleEntity entity) {
        scheduleRepository.save(entity);
    }

    public List<ScheduleEntity> searchList() {
        return scheduleRepository.findAll();
    }
}
