package com.scheduler.daily_scheduler_api.domain.schedule_category.service;

import com.scheduler.daily_scheduler_api.domain.schedule_category.entity.ScheduleCategoryEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@WebAppConfiguration
@Transactional
class ScheduleCategoryServiceTest {

    @Autowired
    private ScheduleCategoryService scheduleCategoryService;

    final static String[] CATEGORY_LIST = {"Work", "Study", "Hobby", "Anything"};

    @Test
    @DisplayName("스케쥴 카테고리 전체조회")
    void 스케쥴_카테고리_전체조회() {
        List<ScheduleCategoryEntity> categories = scheduleCategoryService.searchAll();

        assertThat(categories.size()).isEqualTo(CATEGORY_LIST.length);
        for(int i = 0; i < categories.size(); i++) {
            org.junit.jupiter.api.Assertions.assertTrue(Arrays.asList(CATEGORY_LIST).contains(categories.get(i).getName()));
        }
    }
}