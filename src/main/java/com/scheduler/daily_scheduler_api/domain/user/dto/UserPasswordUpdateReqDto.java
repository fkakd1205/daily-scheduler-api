package com.scheduler.daily_scheduler_api.domain.user.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class UserPasswordUpdateReqDto {
    @NonNull
    private String beforePassword;
    @NonNull
    private String afterPassword;
}
