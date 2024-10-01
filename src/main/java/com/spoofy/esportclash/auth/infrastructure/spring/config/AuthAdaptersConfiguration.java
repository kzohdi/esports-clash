package com.spoofy.esportclash.auth.infrastructure.spring.config;

import com.spoofy.esportclash.auth.application.ports.AuthContext;
import com.spoofy.esportclash.auth.application.ports.UserRepository;
import com.spoofy.esportclash.auth.infrastructure.auth.spring.SpringAuthContext;
import com.spoofy.esportclash.auth.infrastructure.persistence.jpa.SQLUserAccessor;
import com.spoofy.esportclash.auth.infrastructure.persistence.jpa.SQLUserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthAdaptersConfiguration {

    @Bean
    public UserRepository userRepository(EntityManager entityManager, SQLUserAccessor userAccessor) {
        return new SQLUserRepository(entityManager, userAccessor);
    }

    @Bean
    public AuthContext authContext() {
        return new SpringAuthContext();
    }
}
