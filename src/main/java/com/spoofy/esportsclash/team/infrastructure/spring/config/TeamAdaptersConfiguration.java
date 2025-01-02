package com.spoofy.esportsclash.team.infrastructure.spring.config;

import com.spoofy.esportsclash.team.application.ports.TeamQueries;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.infrastructure.persistence.jpa.SQLTeamQueries;
import com.spoofy.esportsclash.team.infrastructure.persistence.jpa.SQLTeamRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeamAdaptersConfiguration {

    @Bean
    TeamRepository teamRepository(EntityManager entityManager) {
        return new SQLTeamRepository(entityManager);
    }

    @Bean
    TeamQueries teamQueries(EntityManager entityManager) {
        return new SQLTeamQueries(entityManager);
    }
}
