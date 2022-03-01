package com.scheduler.daily_scheduler_api.domain.schedule.repository;

import com.scheduler.daily_scheduler_api.domain.schedule.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Integer> {
}
