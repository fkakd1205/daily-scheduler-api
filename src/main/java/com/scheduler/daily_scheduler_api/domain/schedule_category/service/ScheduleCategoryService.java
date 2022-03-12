package com.scheduler.daily_scheduler_api.domain.schedule_category.service;

import com.scheduler.daily_scheduler_api.domain.schedule_category.entity.ScheduleCategoryEntity;
import com.scheduler.daily_scheduler_api.domain.schedule_category.repository.ScheduleCategoryRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleCategoryService {

    private final ScheduleCategoryRepository scheduleCategoryRepository;

    public List<ScheduleCategoryEntity> searchList() {
        return scheduleCategoryRepository.findAll();
    }
}
