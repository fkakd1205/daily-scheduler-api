package com.scheduler.daily_scheduler_api.domain.schedule.controller;

import com.scheduler.daily_scheduler_api.aop.login_check.LoginCheck;
import com.scheduler.daily_scheduler_api.domain.schedule.dto.ScheduleDto;
import com.scheduler.daily_scheduler_api.domain.schedule.dto.ScheduleDtoForCompleted;
import com.scheduler.daily_scheduler_api.domain.schedule.dto.ScheduleSearchReqDto;
import com.scheduler.daily_scheduler_api.domain.schedule.service.ScheduleBusinessService;
import com.scheduler.daily_scheduler_api.domain.user.dto.UserSessionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
     * @see ScheduleBusinessService#createOne
     * @see ScheduleBusinessService#removeSummaryInCache
     */
    @PostMapping("")
    @LoginCheck
    public void createOne(UserSessionDto userSession, @RequestBody ScheduleDto dto) {
        ScheduleDto savedDto = scheduleBusinessService.createOne(userSession, dto);
        scheduleBusinessService.removeSummaryInCache(userSession, savedDto.getCreatedAt());
    }

    /**
     * <b>Search Schedule By Date</b>
     * <p>
     * <b>GET : /api/v1/schedules/date</b>
     * 
     * @param dto : ScheduleSearchReqDto
     * @see ScheduleBusinessService#searchListByDate
     */
    @GetMapping("/date")
    @LoginCheck
    public Object searchListByDate(UserSessionDto userSession, ScheduleSearchReqDto dto) {
        return scheduleBusinessService.searchListByDate(userSession, dto.getStartDate(), dto.getEndDate());
    }

    /**
     * <b>Delete Schedule</b>
     * <p>
     * <b>DELETE : /api/v1/schedules/{id}</b>
     * 
     * @param scheduleId : UUID
     * @see ScheduleBusinessService#deleteOne
     * @see ScheduleBusinessService#removeSummaryInCache
     */
    @DeleteMapping("/{id}")
    @LoginCheck
    public void deleteOne(UserSessionDto userSession, @PathVariable(value="id") UUID scheduleId) {
        ScheduleDto deletedDto = scheduleBusinessService.deleteOne(scheduleId);
        scheduleBusinessService.removeSummaryInCache(userSession, deletedDto.getCreatedAt());
    }

    /**
     * <b>Change Schedule : completed</b>
     * <p>
     * <b>PATCH : /api/v1/schedules/completed</b>
     * 
     * @param dto : ScheduleDtoForCompleted
     * @see ScheduleBusinessService#updateCompeletedSchedule
     * @see ScheduleBusinessService#removeSummaryInCache
     */
    @PatchMapping("/completed")
    @LoginCheck
    public void updateCompeletedSchedule(UserSessionDto userSession, @RequestBody ScheduleDtoForCompleted dto) {
        ScheduleDto updatedDto = scheduleBusinessService.updateCompeletedSchedule(dto);
        scheduleBusinessService.removeSummaryInCache(userSession, updatedDto.getCreatedAt());
    }

    /**
     * <b>Batch Update For Schedule</b>
     * <p>
     * <b>PUT : /api/v1/schedules/batch</b>
     * 
     * @param dtos : List[ScheduleDto]
     * @see ScheduleBusinessService#updateBatch
     * @see ScheduleBusinessService#removeSummaryInCache
     */
    @PutMapping("/batch")
    @LoginCheck
    public void updateBatch(UserSessionDto userSession, @RequestBody List<ScheduleDto> dtos) {
        List<ScheduleDto> updatedDtos = scheduleBusinessService.updateBatch(dtos);
        scheduleBusinessService.removeSummaryInCache(userSession, updatedDtos.get(0).getCreatedAt());
    }

    /**
     * <b>Search Schedule Summary</b>
     * <p>
     * <b>GET : /api/v1/schedules/summary</b>
     * 
     * @param reqDto : ScheduleSearchReqDto
     * @see ScheduleBusinessService#searchSummaryByDate
     */
    @GetMapping("/summary")
    @LoginCheck
    public Object searchSummaryByDate(UserSessionDto userSession, ScheduleSearchReqDto reqDto) {
        return scheduleBusinessService.searchSummaryByDate(userSession, reqDto.getStartDate(), reqDto.getEndDate());
    }

}
