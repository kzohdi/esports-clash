package com.spoofy.esportsclash.auth.usecases;

import com.spoofy.esportsclash.auth.application.port.UserRepository;
import com.spoofy.esportsclash.auth.application.services.jwtservice.JwtService;
import com.spoofy.esportsclash.auth.application.services.jwtservice.JwtServiceImpl;
import com.spoofy.esportsclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import com.spoofy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import com.spoofy.esportsclash.auth.application.usecases.LoginCommand;
import com.spoofy.esportsclash.auth.application.usecases.LoginCommandHandler;
import com.spoofy.esportsclash.auth.domain.model.User;
import com.spoofy.esportsclash.auth.infrastructure.persistence.ram.InMemoryUserRepository;
import com.spoofy.esportsclash.core.domain.exceptions.BadRequestException;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class LoginTests {

    private final UserRepository userRepository = new InMemoryUserRepository();

    private final PasswordHasher passwordHasher = new BcryptPasswordHasher();

    private final JwtService jwtService = new JwtServiceImpl(60);

    private final LoginCommandHandler loginCommandHandler = new LoginCommandHandler(
            userRepository,
            passwordHasher,
            jwtService
    );

    private User user;

    private static final String CLEAR_PASSWORD = "password";

    @BeforeEach
    void setup() {
        userRepository.clear();

        user = new User(
                "123",
                "contact@spoofy.com",
                passwordHasher.hash(CLEAR_PASSWORD)
        );
        userRepository.save(user);
    }

    @Nested
    class HappyPath {

        @Test
        void shouldReturnTheUser() {
            // Given
            var command = new LoginCommand(user.getEmailAddress(), CLEAR_PASSWORD);

            // When
            var result = loginCommandHandler.handle(command);

            var authenticatedUser = jwtService.parse(result.getToken());

            // Then
            assertEquals(user.getId(), authenticatedUser.getId());
            assertEquals(user.getEmailAddress(), authenticatedUser.getEmailAddress());
        }
    }

    @Nested
    class Scenario_TheEmailAddressIsIncorrect {

        @Test
        void shouldThrowNotFound() {
            // Given
            var command = new LoginCommand("contact@spoofy.fr", user.getPassword());

            // When
            var exception = assertThrows(NotFoundException.class, () -> loginCommandHandler.handle(command));

            // Then
            assertEquals("User was not found", exception.getMessage());
        }
    }

    @Nested
    class Scenario_ThePasswordIsIncorrect {

        @Test
        void shouldThrowBadRequest() {
            // Given
            var command = new LoginCommand(user.getEmailAddress(), "azerty");

            // When
            var exception = assertThrows(BadRequestException.class, () -> loginCommandHandler.handle(command));

            // Then
            assertEquals("The password is incorrect", exception.getMessage());
        }
    }
}
