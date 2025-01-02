package com.spoofy.esportsclash.auth.infrastructure.spring.config;

import com.spoofy.esportsclash.auth.application.ports.UserRepository;
import com.spoofy.esportsclash.auth.infrastructure.persistence.jpa.SQLUserDataAccessor;
import com.spoofy.esportsclash.auth.infrastructure.persistence.jpa.SQLUserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthAdaptersConfiguration {

    @Bean
    UserRepository userRepository(EntityManager entityManager, SQLUserDataAccessor dataAccessor) {
        return new SQLUserRepository(entityManager, dataAccessor);
    }
}
