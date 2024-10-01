package com.spoofy.esportclash.auth.infrastructure.spring.config;

import com.spoofy.esportclash.auth.application.services.jwtservice.ConcreteJwtService;
import com.spoofy.esportclash.auth.application.services.jwtservice.JwtService;
import com.spoofy.esportclash.auth.application.services.passwordhasher.BCryptPasswordHasher;
import com.spoofy.esportclash.auth.application.services.passwordhasher.PasswordHasher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthServicesConfiguration {

    @Bean
    public PasswordHasher passwordHasher() {
        return new BCryptPasswordHasher();
    }

    @Bean
    public JwtService jwtService() {
        return new ConcreteJwtService(
                "super_secret_key_please_dont_share",
                3600
        );
    }
}
