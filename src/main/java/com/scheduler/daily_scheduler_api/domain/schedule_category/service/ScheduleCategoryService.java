package com.scheduler.daily_scheduler_api.domain.schedule_category.service;

import com.scheduler.daily_scheduler_api.domain.schedule_category.entity.ScheduleCategoryEntity;
import com.scheduler.daily_scheduler_api.domain.schedule_category.repository.ScheduleCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleCategoryService {

    private ScheduleCategoryRepository scheduleCategoryRepository;

    @Autowired
    public ScheduleCategoryService(ScheduleCategoryRepository scheduleCategoryRepository) {
        this.scheduleCategoryRepository = scheduleCategoryRepository;
    }

    public List<ScheduleCategoryEntity> searchList() {
        return scheduleCategoryRepository.findAll();
    }
}
