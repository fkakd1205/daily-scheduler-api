package com.scheduler.daily_scheduler_api.domain.user.service;

import com.scheduler.daily_scheduler_api.domain.user.dto.req.UserDto;
import com.scheduler.daily_scheduler_api.domain.user.entity.UserEntity;
import com.scheduler.daily_scheduler_api.domain.user.enums.UserStatus;
import com.scheduler.daily_scheduler_api.domain.user.repository.UserRepository;
import com.scheduler.daily_scheduler_api.exception.CustomDuplicateIdException;
import com.scheduler.daily_scheduler_api.exception.CustomNotFoundDataException;
import com.scheduler.daily_scheduler_api.utils.SHA256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void register(UserDto userDto) {
        boolean duplIdResult = isDuplicatedId(userDto.getUserId());
        if (duplIdResult) {
            throw new CustomDuplicateIdException("중복된 아이디입니다.");
        }

        userDto.setCreatedAt(LocalDateTime.now());
        userDto.setPassword(SHA256Util.encryptSHA256(userDto.getPassword()));
        userDto.setStatus(UserStatus.DEFAULT);
        userDto.setIsWithdraw(false);
        if (userDto.getIsAdmin()) {
            userDto.setStatus(UserStatus.ADMIN);
        }
        UserEntity userEntity = UserEntity.toEntity(userDto);
        userRepository.save(userEntity);
    }

    @Override
    public UserDto login(String userId, String password) {
        String cryptoPassword = SHA256Util.encryptSHA256(password);
        UserEntity userEntity = userRepository.findByUserIdAndPassword(userId, cryptoPassword)
                .orElseThrow(() -> new CustomNotFoundDataException("사용자 데이터가 존재하지 않습니다."));

        return UserDto.toDto(userEntity);
    }

    @Override
    public boolean isDuplicatedId(String userId) {
        return userRepository.countByUserId(userId) == 1;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserInfo(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomNotFoundDataException("사용자가 데이터가 존재하지 않습니다."));

        return UserDto.toDto(userEntity);
    }

    @Override
    public void updatePassword(String userId, String beforePassword, String afterPassword) {
        String cryptoPassword = SHA256Util.encryptSHA256(beforePassword);
        Optional<UserEntity> userOpt = userRepository.findByUserIdAndPassword(userId, cryptoPassword);

        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("updatePasswrod ERROR! 비밀번호 변경 메서드를 확인해주세요");
        } else {
            userOpt.get().setPassword(SHA256Util.encryptSHA256(afterPassword));
        }
    }

    @Override
    public void deleteId(String id, String password) {
        String cryptoPassword = SHA256Util.encryptSHA256(password);
        Optional<UserEntity> userOpt = userRepository.findByUserIdAndPassword(id, cryptoPassword);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("deleteId ERROR! id 삭제 메서드를 확인해주세요");
        } else {
            userRepository.deleteById(userOpt.get().getId());
        }
    }
}
