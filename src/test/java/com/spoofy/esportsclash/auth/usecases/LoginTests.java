package com.spoofy.esportsclash.auth.usecases;

import com.spoofy.esportsclash.auth.application.ports.UserRepository;
import com.spoofy.esportsclash.auth.application.services.jwtservice.ConcreteJwtService;
import com.spoofy.esportsclash.auth.application.services.jwtservice.JwtService;
import com.spoofy.esportsclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import com.spoofy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import com.spoofy.esportsclash.auth.application.usecases.LoginCommand;
import com.spoofy.esportsclash.auth.application.usecases.LoginCommandHandler;
import com.spoofy.esportsclash.auth.domain.models.User;
import com.spoofy.esportsclash.auth.infrastructure.persistence.ram.InMemoryUserRepository;
import com.spoofy.esportsclash.core.domain.exceptions.BadRequestException;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class LoginTests {

    private final UserRepository repository = new InMemoryUserRepository();
    private final PasswordHasher passwordHasher = new BcryptPasswordHasher();
    private final JwtService jwtService = new ConcreteJwtService(3600);
    private final LoginCommandHandler commandHandler = new LoginCommandHandler(
            repository,
            passwordHasher,
            jwtService
    );

    @BeforeEach
    void setup() {
        repository.clear();
    }

    @Nested
    class HappyPath {
        @Test
        void shouldReturnTheUser() {
            // Given
            var user = new User(
                    "123",
                    "contact@spoofy.com",
                    passwordHasher.hash("azerty")
            );
            repository.save(user);

            var command = new LoginCommand(user.getEmailAddress(), "azerty");

            // When
            var result = commandHandler.handle(command);

            // Then
            assertThat(result.id()).isEqualTo(user.getId());
            assertThat(result.emailAddress()).isEqualTo(user.getEmailAddress());
        }
    }

    @Nested
    class InvalidEmailAddress {

        @Test
        void shouldThrowNotFound() {
            // Given
            var command = new LoginCommand("contact@spoofy.com", "azerty");

            // When
            // Then
            assertThatThrownBy(() -> commandHandler.handle(command))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("The entity User was not found");
        }
    }

    @Nested
    class InvalidPassword {

        @Test
        void shouldThrowBadRequest() {
            // Given
            var user = new User(
                    "123",
                    "contact@spoofy.com",
                    passwordHasher.hash("azerty")
            );
            repository.save(user);

            var command = new LoginCommand(user.getEmailAddress(), "123456");

            // When
            // Then
            assertThatThrownBy(() -> commandHandler.handle(command))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage("The password is invalid");
        }
    }


}
