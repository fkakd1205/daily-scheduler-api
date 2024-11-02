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
    private Integer id;
    private String userId;
    private String password;
    private String nickname;
    private Boolean isAdmin;
    private LocalDateTime createdAt;
    private Boolean isWithdraw;
    private UserStatus status;
    private LocalDateTime updatedAt;

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
                .isAdmin(userEntity.getIsAdmin())
                .createdAt(userEntity.getCreatedAt())
                .isWithdraw(userEntity.getIsWithdraw())
                .status(userEntity.getStatus())
                .updatedAt(userEntity.getUpdatedAt())
                .build();
    }
}
