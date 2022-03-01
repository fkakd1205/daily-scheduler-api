package com.scheduler.daily_scheduler_api.domain.schedule_category.repository;

import com.scheduler.daily_scheduler_api.domain.schedule_category.entity.ScheduleCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleCategoryRepository extends JpaRepository<ScheduleCategoryEntity, Integer> {
}
