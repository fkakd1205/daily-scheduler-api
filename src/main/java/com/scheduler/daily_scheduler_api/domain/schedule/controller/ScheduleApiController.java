package com.scheduler.daily_scheduler_api.domain.schedule.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.scheduler.daily_scheduler_api.domain.message.Message;
import com.scheduler.daily_scheduler_api.domain.schedule.dto.ScheduleDto;
import com.scheduler.daily_scheduler_api.domain.schedule.service.ScheduleBusinessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;


@CrossOrigin(origins = "http://localhost:3000")
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
     */
    @PostMapping("")
    public ResponseEntity<?> createOne(@RequestBody ScheduleDto dto) {
        scheduleBusinessService.createOne(dto);

        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }

    /**
     * <b>Search Schedule</b>
     * <p>
     * <b>GET : /api/v1/schedules</b>
     * 
     * @return : ResponseEntity
     * @see ScheduleBusinessService#searchList
     */
    // @GetMapping("")
    // public ResponseEntity<?> searchList() {
    //     Message message = Message.builder()
    //             .status(HttpStatus.OK)
    //             .data(scheduleBusinessService.searchList())
    //             .message("success")
    //             .build();

    //     return new ResponseEntity<>(message, message.getStatus());
    // }

    /**
     * <b>Search Schedule By Date</b>
     * <p>
     * <b>GET : /api/v1/schedules/date</b>
     * 
     * @param params : Map[String, Object]
     * @return ResponseEntity
     * @see ScheduleBusinessService#searchListByDate
     */
    @GetMapping("/date")
    public ResponseEntity<?> searchListByDate(@RequestParam Map<String, Object> params) {
        Message message = Message.builder()
                .status(HttpStatus.OK)
                .data(scheduleBusinessService.searchListByDate(params))
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
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable(value="id") UUID scheduleId) {
        scheduleBusinessService.deleteOne(scheduleId);
        
        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }

    /**
     * <b>Change Schedule</b>
     * <p>
     * <b>PATCH : /api/v1/schedules</b>
     * 
     * @param dto : ScheduleDto
     * @return ResponseEntity
     * @see ScheduleBusinessService#patchOne
     */
    @PatchMapping("")
    public ResponseEntity<?> patchOne(@RequestBody ScheduleDto dto) {
        scheduleBusinessService.patchOne(dto);
        
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
     * @param dto : List[ScheduleDto]
     * @return ResponseEntity
     * @see ScheduleBusinessService#updateBatch
     */
    @PutMapping("")
    public ResponseEntity<?> updateBatch(@RequestBody List<ScheduleDto> dtos) {
        scheduleBusinessService.updateBatch(dtos);
        
        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }

}
