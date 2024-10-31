package com.scheduler.daily_scheduler_api.domain.schedule.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.scheduler.daily_scheduler_api.domain.schedule.dto.ScheduleDto;
import com.scheduler.daily_scheduler_api.domain.schedule.dto.ScheduleDtoForCompleted;
import com.scheduler.daily_scheduler_api.domain.schedule.entity.ScheduleEntity;
import com.scheduler.daily_scheduler_api.domain.user.dto.UserSessionDto;
import com.scheduler.daily_scheduler_api.domain.user.dto.req.UserDto;
import com.scheduler.daily_scheduler_api.domain.user.entity.UserEntity;
import com.scheduler.daily_scheduler_api.domain.user.enums.UserStatus;
import com.scheduler.daily_scheduler_api.domain.user.service.UserService;
import com.scheduler.daily_scheduler_api.exception.CustomNotFoundDataException;

import com.scheduler.daily_scheduler_api.utils.SessionUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@WebAppConfiguration
@Transactional
public class ScheduleServiceTest {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleBusinessService scheduleBusinessService;

    @Autowired
    private UserService userService;

    protected MockHttpSession session;
    protected MockHttpServletRequest request;

    ScheduleEntity createEntity(UUID scheduleId) {
        UserDto userDto = userService.getUserInfo("test456");

        ScheduleEntity entity = ScheduleEntity.builder()
                .id(scheduleId)
                .content("할 일")
                .completed(false)
                .createdAt(LocalDateTime.now())
                .categoryId(UUID.fromString("79f7fe0d-6fb3-4b2b-8bfc-1b7402b63275"))
                .user(UserEntity.toEntity(userDto))
                .build();

        return entity;
    }

    @BeforeEach
    void 로그인() {
        String userId = "test456";
        String password = "123";

        UserDto userInfo = userService.login(userId, password);
        UserSessionDto userSessionDto = new UserSessionDto(userInfo.getId(), userInfo.getUserId());

        request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        if (userInfo.getStatus() == (UserStatus.ADMIN)) {
            SessionUtil.setLoginAdminId(request.getSession(), userSessionDto);
        }
        else{
            SessionUtil.setLoginMemberId(request.getSession(), userSessionDto);
        }
    }

    @AfterEach
    void 로그아웃() {
        session = null;
    }

    @Test
    @DisplayName("스케쥴 단일 생성")
    void 스케쥴_단일_생성() {
        // given
        UUID scheduleId = UUID.randomUUID();
        ScheduleEntity entity = createEntity(scheduleId);
    
        // when
        scheduleService.saveAndModify(entity);
        ScheduleEntity resultEntity = scheduleService.searchOne(scheduleId);

        // then
        assertThat(entity.getId()).isEqualTo(resultEntity.getId());
        assertThat(entity.getContent()).isEqualTo(resultEntity.getContent());
    }

    @Test
    @DisplayName("스케쥴 날짜별 조회")
    void 스케쥴_날짜별_조회() {
        // given
        // 실제 client에서 넘어오는 값은 00:00:00 ~ 23:59:59 시간
//        LocalDateTime startDate = LocalDateTime.now().with(LocalTime.MIN);
//        LocalDateTime endDate = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime startDate = LocalDateTime.now().minusSeconds(3);

        UUID scheduleId = UUID.randomUUID();
        ScheduleEntity entity = createEntity(scheduleId);
        scheduleService.saveAndModify(entity);

        // when
        LocalDateTime endDate = LocalDateTime.now().plusSeconds(3);
        UserSessionDto userSession = (UserSessionDto) request.getSession().getAttribute("LOGIN_MEMBER_ID");

        List<ScheduleEntity> scheduleEntities = scheduleService.searchListByDate(userSession.getId(), startDate, endDate);

        // then
        assertThat(scheduleEntities.size()).isEqualTo(1);
        assertThat(entity.getId()).isEqualTo(scheduleEntities.get(0).getId());
        assertThat(entity.getCreatedAt()).isAfter(startDate);
        assertThat(entity.getCreatedAt()).isBefore(endDate);

    }

    @Test
    @DisplayName("스케쥴 단일 제거")
    void 스케쥴_단일_제거() {
        // given
        UUID scheduleId = UUID.randomUUID();
        ScheduleEntity entity = createEntity(scheduleId);
        scheduleService.saveAndModify(entity);

        // when
        ScheduleEntity resultEntity = scheduleService.searchOne(scheduleId);
        scheduleService.deleteOne(resultEntity);

        // then
        org.junit.jupiter.api.Assertions.assertThrows(CustomNotFoundDataException.class,
        () -> scheduleService.searchOne(resultEntity.getId()));
    }

    @Test
    @DisplayName("스케쥴 단일 완료여부 수정")
    void 스케쥴_단일_완료여부_수정() {
        // given
        UUID scheduleId = UUID.randomUUID();
        ScheduleEntity entity = createEntity(scheduleId);
        scheduleService.saveAndModify(entity);

        ScheduleDtoForCompleted changedDto = ScheduleDtoForCompleted.builder()
            .id(entity.getId())
            .completed(true)
            .build();

        // when
        scheduleBusinessService.updateCompeletedSchedule(changedDto);
        ScheduleEntity resultEntity = scheduleService.searchOne(changedDto.getId());
        
        // then
        assertThat(resultEntity.getId()).isEqualTo(entity.getId());
        assertThat(resultEntity.getCompleted()).isTrue();
        assertThat(resultEntity.getUpdatedAt()).isAfter(entity.getCreatedAt());

        assertThat(resultEntity.getContent()).isEqualTo(entity.getContent());
    }

    @Test
    @DisplayName("스케쥴 다중 수정")
    void 스케쥴_다중_수정() {
        // given
        List<ScheduleEntity> entities = new ArrayList<>();

        UUID scheduleId1 = UUID.randomUUID();
        ScheduleEntity entity1 = createEntity(scheduleId1);

        UUID scheduleId2 = UUID.randomUUID();
        ScheduleEntity entity2 = createEntity(scheduleId2);
        entity2.setCompleted(true);

        entities.add(entity1);
        entities.add(entity2);

        scheduleService.saveListAndModify(entities);

        // when
        List<ScheduleDto> dtos = new ArrayList<>();

        ScheduleDto dto1 = ScheduleDto.builder()
            .id(scheduleId1)
            .content(entity1.getContent())
            .completed(true)
            .categoryId(entity1.getCategoryId())
            .build();

        ScheduleDto dto2 = ScheduleDto.builder()
            .id(scheduleId2)
            .content(entity2.getContent())
            .completed(entity2.getCompleted())
            .categoryId(UUID.fromString("c2737ba4-75a3-4d32-b6b3-88bfa14f5d5d"))
            .build();

        dtos.add(dto1);
        dtos.add(dto2);

        scheduleBusinessService.updateBatch(dtos);

        ScheduleEntity resultEntity1 = scheduleService.searchOne(scheduleId1);
        ScheduleEntity resultEntity2 = scheduleService.searchOne(scheduleId2);

        // then
        assertThat(resultEntity1.getCompleted()).isTrue();
        assertThat(resultEntity2.getCategoryId()).isEqualTo(entity2.getCategoryId());
        assertThat(resultEntity2.getCategoryId()).isEqualTo(dto2.getCategoryId());
    }
}
