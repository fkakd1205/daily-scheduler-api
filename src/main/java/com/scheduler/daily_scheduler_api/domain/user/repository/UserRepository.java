package com.scheduler.daily_scheduler_api.domain.user.repository;

import com.scheduler.daily_scheduler_api.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUserId(String userId);

    @Query("SELECT user FROM UserEntity user WHERE user.userId = :userId AND user.password = :password")
    Optional<UserEntity> findByUserIdAndPassword(@Param("userId") String userId, @Param("password") String password);

    @Query("SELECT COUNT(user) FROM UserEntity user WHERE user.userId = :userId")
    int countByUserId(@Param("userId") String userId);
}
