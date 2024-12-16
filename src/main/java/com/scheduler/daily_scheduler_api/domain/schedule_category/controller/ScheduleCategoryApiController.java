package com.scheduler.daily_scheduler_api.domain.schedule_category.controller;

import com.scheduler.daily_scheduler_api.domain.schedule_category.service.ScheduleCategoryBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schedule-categories")
@RequiredArgsConstructor
public class ScheduleCategoryApiController {
    private final ScheduleCategoryBusinessService scheduleCategoryBusinessService;

    /**
     * <b>Search All Schedule Categories</b>
     * <p>
     * <b>GET : /api/v1/schedule-categories/all</b>
     *
     * @see ScheduleCategoryBusinessService#searchAll
     */
    @GetMapping("/all")
    public Object searchAll() {
        return scheduleCategoryBusinessService.searchAll();
    }
}
