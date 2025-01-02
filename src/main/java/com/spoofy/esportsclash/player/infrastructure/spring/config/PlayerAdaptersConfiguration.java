package com.spoofy.esportsclash.player.infrastructure.spring.config;

import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.models.Player;
import com.spoofy.esportsclash.player.infrastructure.persistence.jpa.SQLPlayerRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerAdaptersConfiguration {

    @Bean
    PlayerRepository playerRepository(EntityManager entityManager) {
        return new SQLPlayerRepository(entityManager);
    }
}
