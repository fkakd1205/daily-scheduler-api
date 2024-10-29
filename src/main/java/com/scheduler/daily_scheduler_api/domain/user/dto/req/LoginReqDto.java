package com.scheduler.daily_scheduler_api.domain.user.dto.req;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class LoginReqDto {
    @NonNull
    private String userId;
    @NonNull
    private String password;
}
