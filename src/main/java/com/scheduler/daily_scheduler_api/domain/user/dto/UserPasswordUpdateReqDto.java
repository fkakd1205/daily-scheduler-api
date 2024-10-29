package com.scheduler.daily_scheduler_api.domain.user.dto;

import lombok.*;

@Setter
@Getter
public class UserPasswordUpdateReqDto {
    @NonNull
    private String beforePassword;
    @NonNull
    private String afterPassword;
}
