package com.scheduler.daily_scheduler_api.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserSessionDto {
    private Integer id;
    private String userId;
    private boolean isAdmin;
}
