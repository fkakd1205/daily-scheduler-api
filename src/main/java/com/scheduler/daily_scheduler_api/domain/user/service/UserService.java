package com.scheduler.daily_scheduler_api.domain.user.service;

import com.scheduler.daily_scheduler_api.domain.user.dto.req.UserDto;

public interface UserService {
    void register(UserDto userDto);

    UserDto login(String userId, String password);

    boolean isDuplicatedId(String userId);

    UserDto getUserInfo(String userId);

    void updatePassword(String userId, String beforePassword, String afterPassword);

    void deleteId(String userId, String password);
}
