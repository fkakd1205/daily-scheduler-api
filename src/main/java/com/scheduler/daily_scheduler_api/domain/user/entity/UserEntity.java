package com.scheduler.daily_scheduler_api.domain.user.entity;

import com.scheduler.daily_scheduler_api.domain.user.dto.req.UserDto;
import com.scheduler.daily_scheduler_api.domain.user.enums.UserStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userId;
    @Setter
    private String password;
    private String nickname;
    private Boolean isAdmin;
    private LocalDateTime createTime;
    @Setter
    private Boolean isWithdraw;
    @Setter
    @Enumerated(value = EnumType.STRING)
    private UserStatus status;
    @Setter
    private LocalDateTime updateTime;

    public static UserEntity toEntity(UserDto userDto) {
        return UserEntity.builder()
                .id(userDto.getId())
                .userId(userDto.getUserId())
                .password(userDto.getPassword())
                .nickname(userDto.getNickname())
                .isAdmin(userDto.getIsAdmin())
                .createTime(userDto.getCreateTime())
                .isWithdraw(userDto.getIsWithdraw())
                .status(userDto.getStatus())
                .updateTime(userDto.getUpdateTime())
                .build();
    }
}
