package com.spoofy.esportsclash.auth.infrastructure.spring.config;

import com.spoofy.esportsclash.auth.application.ports.UserRepository;
import com.spoofy.esportsclash.auth.application.services.jwtservice.JwtService;
import com.spoofy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import com.spoofy.esportsclash.auth.application.usecases.LoginCommandHandler;
import com.spoofy.esportsclash.auth.application.usecases.RegisterCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthUseCasesConfiguration {

    @Bean
    RegisterCommandHandler registerCommandHandler(UserRepository repository) {
        return new RegisterCommandHandler(repository);
    }

    @Bean
    LoginCommandHandler loginCommandHandler(UserRepository repository, PasswordHasher passwordHasher, JwtService jwtService) {
        return new LoginCommandHandler(repository, passwordHasher, jwtService);
    }
}
