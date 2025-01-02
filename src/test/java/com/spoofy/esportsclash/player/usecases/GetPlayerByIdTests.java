package com.spoofy.esportsclash.player.usecases;

import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.application.usecases.GetPlayerByIdCommand;
import com.spoofy.esportsclash.player.application.usecases.GetPlayerByIdCommandHandler;
import com.spoofy.esportsclash.player.domain.models.Player;
import com.spoofy.esportsclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GetPlayerByIdTests {

    private final PlayerRepository repository = new InMemoryPlayerRepository();
    private final GetPlayerByIdCommandHandler commandHandler = new GetPlayerByIdCommandHandler(repository);

    @Test
    void shouldGetPlayerById() {
        // Given
        repository.save(new Player("123", "Spoofy"));

        var command = new GetPlayerByIdCommand("123");

        // When
        var result = commandHandler.handle(command);

        // Then
        assertThat(result.id()).isEqualTo("123");
        assertThat(result.name()).isEqualTo("Spoofy");
    }

    @Test
    void whenPlayerNotFound_shouldThrow() {
        // Given
        var command = new GetPlayerByIdCommand("123");

        // When
        // Then
        assertThatThrownBy(() -> commandHandler.handle(command))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("The entity Player with the id 123 was not found");
    }
}
