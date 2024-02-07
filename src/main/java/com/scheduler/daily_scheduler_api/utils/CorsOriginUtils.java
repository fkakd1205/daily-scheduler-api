package com.scheduler.daily_scheduler_api.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CorsOriginUtils {
    private static String ORIGIN_MAIN;
    
    @Value("${scheduler.origin.main}")
    public void setOriginMain(String originMain) {
        this.ORIGIN_MAIN = originMain;
    }

    public static String getOriginMain() {
        return ORIGIN_MAIN;
    }
}
