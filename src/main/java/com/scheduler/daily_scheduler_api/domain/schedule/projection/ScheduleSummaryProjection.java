package com.scheduler.daily_scheduler_api.domain.schedule.projection;

import java.util.Date;

public interface ScheduleSummaryProjection {
    Date getDatetime();
    Integer getCompletionCount();
    Integer getRegistrationCount();
}
