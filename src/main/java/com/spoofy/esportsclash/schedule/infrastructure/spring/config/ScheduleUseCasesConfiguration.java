package com.spoofy.esportsclash.schedule.infrastructure.spring.config;

import com.spoofy.esportsclash.schedule.application.ports.ScheduleDayRepository;
import com.spoofy.esportsclash.schedule.application.usecases.CancelMatchCommandHandler;
import com.spoofy.esportsclash.schedule.application.usecases.OrganizeMatchCommandHandler;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduleUseCasesConfiguration {

    @Bean
    OrganizeMatchCommandHandler organizeMatchCommandHandler(ScheduleDayRepository scheduleDayRepository, TeamRepository teamRepository) {
        return new OrganizeMatchCommandHandler(scheduleDayRepository, teamRepository);
    }

    @Bean
    CancelMatchCommandHandler commandHandler(ScheduleDayRepository scheduleDayRepository) {
        return new CancelMatchCommandHandler(scheduleDayRepository);
    }
}
