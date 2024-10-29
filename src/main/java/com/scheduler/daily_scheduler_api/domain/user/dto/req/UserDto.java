package com.scheduler.daily_scheduler_api.domain.user.dto.req;

import com.scheduler.daily_scheduler_api.domain.user.entity.UserEntity;
import com.scheduler.daily_scheduler_api.domain.user.enums.UserStatus;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int id;
    private String userId;
    private String password;
    private String nickname;
    private boolean isAdmin;
    private LocalDateTime createTime;
    private boolean isWithdraw;
    private UserStatus status;
    private LocalDateTime updateTime;

    public static boolean hasNullDataBeforeSignUp(UserDto userDto) {
        return userDto.getUserId() == null || userDto.getPassword() == null
                || userDto.getNickname() == null;
    }

    public static UserDto toDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .userId(userEntity.getUserId())
                .password(userEntity.getPassword())
                .nickname(userEntity.getNickname())
                .isAdmin(userEntity.isAdmin())
                .createTime(userEntity.getCreateTime())
                .isWithdraw(userEntity.isWithdraw())
                .status(userEntity.getStatus())
                .updateTime(userEntity.getUpdateTime())
                .build();
    }
}
