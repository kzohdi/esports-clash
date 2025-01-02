package com.spoofy.esportsclash.auth.usecases;

import com.spoofy.esportsclash.auth.application.exceptions.EmailAlreadyInUseException;
import com.spoofy.esportsclash.auth.application.ports.UserRepository;
import com.spoofy.esportsclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import com.spoofy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import com.spoofy.esportsclash.auth.application.usecases.RegisterCommand;
import com.spoofy.esportsclash.auth.application.usecases.RegisterCommandHandler;
import com.spoofy.esportsclash.auth.domain.models.User;
import com.spoofy.esportsclash.auth.infrastructure.persistence.ram.InMemoryUserRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class RegisterTests {

    private final UserRepository repository = new InMemoryUserRepository();
    private final RegisterCommandHandler commandHandler = new RegisterCommandHandler(repository);
    private final PasswordHasher passwordHasher = new BcryptPasswordHasher();

    @Test
    void shouldRegister() {
        // Given
        var command = new RegisterCommand(
                "test@spoofy.com",
                "123456"
        );

        // When
        var result = commandHandler.handle(command);

        // Then
        var actualUser = repository.findById(result.id()).get();
        assertThat(actualUser.getEmailAddress()).isEqualTo(command.emailAddress());
        assertThat(
                passwordHasher.matches(
                        command.clearPassword(),
                        actualUser.getPasswordHash())
        ).isTrue();
    }

    @Test
    void whenEmailAlreadyInUse_shouldFail() {
        // Given
        var existingUser = new User("123", "test@spoofy.com", "123456");
        repository.save(existingUser);

        var command = new RegisterCommand(existingUser.getEmailAddress(), existingUser.getPasswordHash());

        // When
        // Then
        assertThatThrownBy(() -> commandHandler.handle(command))
                .isInstanceOf(EmailAlreadyInUseException.class)
                .hasMessage("The email address is already in use");
    }
}
