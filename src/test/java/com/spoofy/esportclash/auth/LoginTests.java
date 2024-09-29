package com.spoofy.esportclash.auth;

import com.spoofy.esportclash.auth.application.ports.UserRepository;
import com.spoofy.esportclash.auth.application.services.jwtservice.ConcreteJwtService;
import com.spoofy.esportclash.auth.application.services.jwtservice.JwtService;
import com.spoofy.esportclash.auth.application.services.passwordhasher.BCryptPasswordHasher;
import com.spoofy.esportclash.auth.application.services.passwordhasher.PasswordHasher;
import com.spoofy.esportclash.auth.application.usecases.LoginCommand;
import com.spoofy.esportclash.auth.application.usecases.LoginCommandHandler;
import com.spoofy.esportclash.auth.domain.model.User;
import com.spoofy.esportclash.auth.infrastructure.persistence.ram.InMemoryUserRepository;
import com.spoofy.esportclash.core.domain.exceptions.BadRequestException;
import com.spoofy.esportclash.core.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginTests {
    private final UserRepository repository = new InMemoryUserRepository();
    private final JwtService jwtService = new ConcreteJwtService(
            "super_secret_key_please_dont_share",
            60
    );
    private final PasswordHasher passwordHasher = new BCryptPasswordHasher();

    private final User user = new User(
            "123",
            "contact@spoofy.com",
            passwordHasher.hash("azerty")
    );

    private LoginCommandHandler createHandler() {
        return new LoginCommandHandler(
                repository,
                jwtService,
                passwordHasher
        );
    }

    @BeforeEach
    void setup() {
        repository.clear();
        repository.save(user);
    }

    @Nested
    class HappyPath {
        @Test
        void shouldReturnTheUser() {
            var command = new LoginCommand(
                    "contact@spoofy.com",
                    "azerty"
            );
            var commandHandler = createHandler();

            var result = commandHandler.handle(command);

            assertEquals(user.getId(), result.getId());
            assertEquals(user.getEmailAddress(), result.getEmailAddress());

            var authenticatedUser = jwtService.parse(result.getToken());

            assertEquals(user.getId(), authenticatedUser.getId());
            assertEquals(user.getEmailAddress(), authenticatedUser.getEmailAddress());
        }
    }

    @Nested
    class Scenario_TheEmailAddressIsIncorrect {
        @Test
        void shouldThrowNotFound() {
            var command = new LoginCommand(
                    "johndoe@spoofy.com",
                    "azerty"
            );
            var commandHandler = createHandler();

            var exception = assertThrows(NotFoundException.class, () -> commandHandler.handle(command));

            assertEquals("The entity User was not found", exception.getMessage());
        }
    }

    @Nested
    class Scenario_ThePasswordIsIncorrect {
        @Test
        void shouldThrowNotFound() {
            var command = new LoginCommand(
                    "contact@spoofy.com",
                    "password"
            );
            var commandHandler = createHandler();

            var exception = assertThrows(BadRequestException.class, () -> commandHandler.handle(command));

            assertEquals("The password is incorrect", exception.getMessage());
        }
    }
}
