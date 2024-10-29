package com.scheduler.daily_scheduler_api.domain.user.dto.res;

import com.scheduler.daily_scheduler_api.domain.user.dto.req.UserDto;
import com.scheduler.daily_scheduler_api.domain.user.enums.LoginStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginResDto {

    @NonNull
    private LoginStatus result;
    private UserDto userDto;

    private static final LoginResDto FAIL = new LoginResDto(LoginStatus.FAIL);

    public static LoginResDto success(UserDto userDto) {
        return new LoginResDto(LoginStatus.SUCCESS, userDto);
    }
}