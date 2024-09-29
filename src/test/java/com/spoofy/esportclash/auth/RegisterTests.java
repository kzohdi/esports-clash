package com.spoofy.esportclash.auth;

import com.spoofy.esportclash.auth.application.exceptions.EmailAddressUnavailableException;
import com.spoofy.esportclash.auth.application.ports.UserRepository;
import com.spoofy.esportclash.auth.application.services.passwordhasher.BCryptPasswordHasher;
import com.spoofy.esportclash.auth.application.services.passwordhasher.PasswordHasher;
import com.spoofy.esportclash.auth.application.usecases.RegisterCommand;
import com.spoofy.esportclash.auth.application.usecases.RegisterCommandHandler;
import com.spoofy.esportclash.auth.domain.model.User;
import com.spoofy.esportclash.auth.infrastructure.persistence.ram.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterTests {

    private final UserRepository repository = new InMemoryUserRepository();
    private final PasswordHasher passwordHasher = new BCryptPasswordHasher();

    public RegisterCommandHandler createHandler() {
        return new RegisterCommandHandler(repository, passwordHasher);
    }

    @BeforeEach
    void setup() {
        repository.clear();
    }

    @Test
    void shouldRegister() {
        var command = new RegisterCommand(
                "contact@spoofy.com",
                "azerty"
        );
        var commandHandler = createHandler();

        var response = commandHandler.handle(command);

        var actualUser = repository.findById(response.getId()).get();

        assertEquals(command.getEmailAddress(), actualUser.getEmailAddress());
        assertTrue(
                passwordHasher.match(
                        command.getPassword(),
                        actualUser.getPasswordHash())
        );
    }

    @Test
    void whenEmailAddressIsInUse_shouldFail() {
        var existingUser = new User("123",
                "contact@spoofy.com",
                "password");
        repository.save(existingUser);

        var command = new RegisterCommand(
                existingUser.getEmailAddress(),
                "azerty"
        );
        var commandHandler = createHandler();

        var exception = assertThrows(EmailAddressUnavailableException.class, () ->
                commandHandler.handle(command));

        assertEquals("The email address is already in use", exception.getMessage());
    }
}
