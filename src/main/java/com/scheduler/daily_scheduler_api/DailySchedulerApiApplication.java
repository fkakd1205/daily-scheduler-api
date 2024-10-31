package com.scheduler.daily_scheduler_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class DailySchedulerApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(DailySchedulerApiApplication.class, args);
	}

}
