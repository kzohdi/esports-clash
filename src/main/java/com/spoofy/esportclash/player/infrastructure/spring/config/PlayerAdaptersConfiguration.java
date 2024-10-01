package com.spoofy.esportclash.player.infrastructure.spring.config;

import com.spoofy.esportclash.player.application.ports.PlayerRepository;
import com.spoofy.esportclash.player.infrastructure.persistence.jpa.SQLPlayerRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerAdaptersConfiguration {

    @Bean
    public PlayerRepository playerRepository(EntityManager entityManager) {
        return new SQLPlayerRepository(entityManager);
    }
}
