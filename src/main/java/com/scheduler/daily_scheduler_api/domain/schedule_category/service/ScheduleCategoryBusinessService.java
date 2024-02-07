package com.scheduler.daily_scheduler_api.domain.schedule_category.service;

import com.scheduler.daily_scheduler_api.domain.schedule_category.dto.ScheduleCategoryDto;
import com.scheduler.daily_scheduler_api.domain.schedule_category.entity.ScheduleCategoryEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleCategoryBusinessService {
    private final ScheduleCategoryService scheduleCategoryService;

    /**
     * <b>DB Select Related Method</b>
     * <p>
     * schedule category를 모두 조회한다.
     * 
     * @return List[ScheduleCategoryDto]
     * @see ScheduleCategoryService#searchAll
     * @see ScheduleCategoryDto#toDto
     */
    @Transactional(readOnly = true)
    public List<ScheduleCategoryDto> searchAll() {
        List<ScheduleCategoryEntity> entities = scheduleCategoryService.searchAll();
        List<ScheduleCategoryDto> dtos = entities.stream().map(r -> ScheduleCategoryDto.toDto(r)).collect(Collectors.toList());
        return dtos;
    }
}
