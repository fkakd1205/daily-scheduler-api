package com.scheduler.daily_scheduler_api.domain.schedule.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.scheduler.daily_scheduler_api.domain.schedule.entity.ScheduleEntity;
import com.scheduler.daily_scheduler_api.domain.schedule.projection.ScheduleSummaryProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Integer> {

    Optional<ScheduleEntity> findById(UUID id);

    @Query("SELECT sc\n"
        + "FROM ScheduleEntity sc\n"
        + "WHERE sc.id IN :idList"
    )
    List<ScheduleEntity> findAllById(List<UUID> idList);

    @Query("SELECT sc\n"
        + "FROM ScheduleEntity sc\n"
        + "WHERE sc.createdAt BETWEEN :startDate AND :endDate"
    )
    List<ScheduleEntity> findAllByDate(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT DATE(sc.createdAt) as datetime, COUNT(sc) as incompletionCount, "
        + "SUM(CASE WHEN sc.completed is TRUE THEN 1 ELSE 0 END) as completionCount\n"
        + "FROM ScheduleEntity sc\n"
        + "WHERE sc.createdAt BETWEEN :startDate AND :endDate\n"
        + "GROUP BY datetime"
    )
    List<ScheduleSummaryProjection> findSummaryByDate(LocalDateTime startDate, LocalDateTime endDate);
}
