package com.scheduler.daily_scheduler_api.domain.schedule.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.scheduler.daily_scheduler_api.aop.LoginCheck;
import com.scheduler.daily_scheduler_api.domain.message.Message;
import com.scheduler.daily_scheduler_api.domain.schedule.dto.ScheduleDto;
import com.scheduler.daily_scheduler_api.domain.schedule.dto.ScheduleDtoForCompleted;
import com.scheduler.daily_scheduler_api.domain.schedule.dto.ScheduleSearchReqDto;
import com.scheduler.daily_scheduler_api.domain.schedule.service.ScheduleBusinessService;

import com.scheduler.daily_scheduler_api.domain.user.dto.UserSessionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleApiController {
    private final ScheduleBusinessService scheduleBusinessService;

    /**
     * <b>Create Schedule</b>
     * <p>
     * <b>POST : /api/v1/schedules</b>
     * 
     * @param dto : ScheduleDto
     * @return : ResponseEntity
     * @see ScheduleBusinessService#createOne
     * @see ScheduleBusinessService#removeSummaryInCache
     */
    @PostMapping("")
    @LoginCheck
    public ResponseEntity<?> createOne(UserSessionDto userSession, @RequestBody ScheduleDto dto) {
        ScheduleDto savedDto = scheduleBusinessService.createOne(userSession, dto);
        scheduleBusinessService.removeSummaryInCache(userSession, savedDto.getCreatedAt());

        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }

    /**
     * <b>Search Schedule By Date</b>
     * <p>
     * <b>GET : /api/v1/schedules/date</b>
     * 
     * @param dto : ScheduleSearchReqDto
     * @return ResponseEntity
     * @see ScheduleBusinessService#searchListByDate
     */
    @GetMapping("/date")
    @LoginCheck
    public ResponseEntity<?> searchListByDate(UserSessionDto userSession, ScheduleSearchReqDto dto) {
        Message message = Message.builder()
                .status(HttpStatus.OK)
                .data(scheduleBusinessService.searchListByDate(userSession, dto.getStartDate(), dto.getEndDate()))
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }

    /**
     * <b>Delete Schedule</b>
     * <p>
     * <b>DELETE : /api/v1/schedules/{id}</b>
     * 
     * @param scheduleId : UUID
     * @return ResponseEntity
     * @see ScheduleBusinessService#deleteOne
     * @see ScheduleBusinessService#removeSummaryInCache
     */
    @DeleteMapping("/{id}")
    @LoginCheck
    public ResponseEntity<?> deleteOne(UserSessionDto userSession, @PathVariable(value="id") UUID scheduleId) {
        ScheduleDto deletedDto = scheduleBusinessService.deleteOne(scheduleId);
        scheduleBusinessService.removeSummaryInCache(userSession, deletedDto.getCreatedAt());
        
        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }

    /**
     * <b>Change Schedule : completed</b>
     * <p>
     * <b>PATCH : /api/v1/schedules/completed</b>
     * 
     * @param dto : ScheduleDtoForCompleted
     * @return ResponseEntity
     * @see ScheduleBusinessService#updateCompeletedSchedule
     * @see ScheduleBusinessService#removeSummaryInCache
     */
    @PatchMapping("/completed")
    @LoginCheck
    public ResponseEntity<?> updateCompeletedSchedule(UserSessionDto userSession, @RequestBody ScheduleDtoForCompleted dto) {
        ScheduleDto updatedDto = scheduleBusinessService.updateCompeletedSchedule(dto);
        scheduleBusinessService.removeSummaryInCache(userSession, updatedDto.getCreatedAt());
        
        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }

    /**
     * <b>Update Schedule</b>
     * <p>
     * <b>PUT : /api/v1/schedules</b>
     * 
     * @param dtos : List[ScheduleDto]
     * @return ResponseEntity
     * @see ScheduleBusinessService#updateBatch
     * @see ScheduleBusinessService#removeSummaryInCache
     */
    @PutMapping("/batch")
    @LoginCheck
    public ResponseEntity<?> updateBatch(UserSessionDto userSession, @RequestBody List<ScheduleDto> dtos) {
        List<ScheduleDto> updatedDtos = scheduleBusinessService.updateBatch(dtos);
        ScheduleDto defaultUpdatedDto = updatedDtos.get(0);
        scheduleBusinessService.removeSummaryInCache(userSession, defaultUpdatedDto.getCreatedAt());

        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }

    /**
     * <b>Search Schedule Summary By params</b>
     * <p>
     * <b>GET : /api/v1/schedules/summary</b>
     * 
     * @param reqDto : ScheduleSearchReqDto
     * @return ResponseEntity
     * @see ScheduleBusinessService#searchSummaryByDate
     */
    @GetMapping("/summary")
    @LoginCheck
    public ResponseEntity<?> searchSummaryByDate(UserSessionDto userSession, ScheduleSearchReqDto reqDto) {
        Message message = Message.builder()
                .status(HttpStatus.OK)
                .data(scheduleBusinessService.searchSummaryByDate(userSession, reqDto.getStartDate(), reqDto.getEndDate()))
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }

}
