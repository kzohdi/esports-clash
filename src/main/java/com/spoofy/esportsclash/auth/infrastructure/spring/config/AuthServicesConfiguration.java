package com.spoofy.esportsclash.auth.infrastructure.spring.config;

import com.spoofy.esportsclash.auth.application.services.jwtservice.ConcreteJwtService;
import com.spoofy.esportsclash.auth.application.services.jwtservice.JwtService;
import com.spoofy.esportsclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import com.spoofy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthServicesConfiguration {

    @Bean
    PasswordHasher passwordHasher() {
        return new BcryptPasswordHasher();
    }

    @Bean
    JwtService jwtService() {
        return new ConcreteJwtService(3600);
    }
}
