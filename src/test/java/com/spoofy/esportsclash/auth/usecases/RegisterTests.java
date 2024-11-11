package com.spoofy.esportsclash.auth.usecases;

import com.spoofy.esportsclash.auth.application.exceptions.EmailAddressAlreadyInUseException;
import com.spoofy.esportsclash.auth.application.port.UserRepository;
import com.spoofy.esportsclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import com.spoofy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import com.spoofy.esportsclash.auth.application.usecases.RegisterCommand;
import com.spoofy.esportsclash.auth.application.usecases.RegisterCommandHandler;
import com.spoofy.esportsclash.auth.domain.model.User;
import com.spoofy.esportsclash.auth.infrastructure.persistence.ram.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterTests {

    private final UserRepository userRepository = new InMemoryUserRepository();

    private final PasswordHasher passwordHasher = new BcryptPasswordHasher();

    private final RegisterCommandHandler registerCommandHandler = new RegisterCommandHandler(userRepository, passwordHasher);

    @BeforeEach
    void setup() {
        userRepository.clear();
    }

    @Test
    void shouldRegister() {
        // Given
        var command = new RegisterCommand("contact@spoofy.com", "password");

        // When
        var result = registerCommandHandler.handle(command);

        var actualUser = userRepository.findById(result.getId()).get();

        // Then
        assertEquals(command.emailAddress(), actualUser.getEmailAddress());
        assertTrue(passwordHasher.match(command.password(), actualUser.getPassword()));
    }

    @Test
    void whenEmailIsAlreadyInUse_shouldThrow() {
        // Given
        var existingUser = new User("123", "contact@spoofy.com", "password");
        userRepository.save(existingUser);

        var command = new RegisterCommand(existingUser.getEmailAddress(), "password");

        // When
        var exception = assertThrows(EmailAddressAlreadyInUseException.class, () -> registerCommandHandler.handle(command));

        // Then
        assertEquals("Email address is already in use", exception.getMessage());
    }
}
