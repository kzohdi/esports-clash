package com.spoofy.esportclash.auth.infrastructure.spring.config;

import com.spoofy.esportclash.auth.application.ports.UserRepository;
import com.spoofy.esportclash.auth.application.services.jwtservice.JwtService;
import com.spoofy.esportclash.auth.application.services.passwordhasher.PasswordHasher;
import com.spoofy.esportclash.auth.application.usecases.LoginCommandHandler;
import com.spoofy.esportclash.auth.application.usecases.RegisterCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthUseCasesConfiguration {

    @Bean
    public RegisterCommandHandler registerCommandHandler(
            UserRepository repository,
            PasswordHasher passwordHasher
    ) {
        return new RegisterCommandHandler(repository, passwordHasher);
    }

    @Bean
    public LoginCommandHandler loginCommandHandler(
            UserRepository repository,
            JwtService jwtService,
            PasswordHasher passwordHasher
    ) {
        return new LoginCommandHandler(repository, jwtService, passwordHasher);
    }
}
