package com.scheduler.daily_scheduler_api.domain.schedule_category.service;

import com.scheduler.daily_scheduler_api.domain.schedule_category.dto.ScheduleCategoryDto;
import com.scheduler.daily_scheduler_api.domain.schedule_category.entity.ScheduleCategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleCategoryBusinessService {

    private ScheduleCategoryService scheduleCategoryService;

    @Autowired
    public ScheduleCategoryBusinessService (ScheduleCategoryService scheduleCategoryService) {
        this.scheduleCategoryService = scheduleCategoryService;
    }

    public List<ScheduleCategoryDto> searchList() {
        List<ScheduleCategoryEntity> entities = scheduleCategoryService.searchList();
        List<ScheduleCategoryDto> dtos = entities.stream().map(r -> ScheduleCategoryDto.toDto(r)).collect(Collectors.toList());
        return dtos;
    }
}
