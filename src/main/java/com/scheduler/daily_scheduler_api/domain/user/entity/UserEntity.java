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
    @Column(name = "is_admin")
    private Boolean isAdmin;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Setter
    @Column(name = "is_withdraw")
    private Boolean isWithdraw;
    @Setter
    @Enumerated(value = EnumType.STRING)
    private UserStatus status;
    @Setter
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static UserEntity toEntity(UserDto userDto) {
        return UserEntity.builder()
                .id(userDto.getId())
                .userId(userDto.getUserId())
                .password(userDto.getPassword())
                .nickname(userDto.getNickname())
                .isAdmin(userDto.getIsAdmin())
                .createdAt(userDto.getCreatedAt())
                .isWithdraw(userDto.getIsWithdraw())
                .status(userDto.getStatus())
                .updatedAt(userDto.getUpdatedAt())
                .build();
    }
}
