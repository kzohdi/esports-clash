package com.spoofy.esportclash.team.infrastucture.spring.config;

import com.spoofy.esportclash.team.application.ports.TeamQueries;
import com.spoofy.esportclash.team.application.ports.TeamRepository;
import com.spoofy.esportclash.team.infrastucture.persistence.jpa.SQLTeamQueries;
import com.spoofy.esportclash.team.infrastucture.persistence.jpa.SQLTeamRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeamAdaptersConfiguration {

    @Bean
    public TeamRepository teamRepository(EntityManager entityManager) {
        return new SQLTeamRepository(entityManager);
    }

    @Bean
    public TeamQueries teamQueries(EntityManager entityManager) {
        return new SQLTeamQueries(entityManager);
    }
}
