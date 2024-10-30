package com.scheduler.daily_scheduler_api.domain.schedule.projection;

import com.scheduler.daily_scheduler_api.domain.user.entity.UserEntity;

import java.util.Date;

public interface ScheduleSummaryProjection {
    Date getDatetime();
    Integer getCompletionCount();
    Integer getRegistrationCount();
    UserEntity getUser();
}
