package com.spoofy.esportsclash.schedule.infrastructure.spring.config;

import com.spoofy.esportsclash.schedule.application.ports.ScheduleDayRepository;
import com.spoofy.esportsclash.schedule.infrastructure.persistence.jpa.SQLScheduleDayRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduleAdaptersConfiguration {

    @Bean
    ScheduleDayRepository scheduleDayRepository(EntityManager entityManager) {
        return new SQLScheduleDayRepository(entityManager);
    }
}
