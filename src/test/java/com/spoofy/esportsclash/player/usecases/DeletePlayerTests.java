package com.spoofy.esportsclash.player.usecases;

import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.application.usecases.DeletePlayerCommand;
import com.spoofy.esportsclash.player.application.usecases.DeletePlayerCommandHandler;
import com.spoofy.esportsclash.player.domain.models.Player;
import com.spoofy.esportsclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeletePlayerTests {

    private final PlayerRepository repository = new InMemoryPlayerRepository();
    private final DeletePlayerCommandHandler commandHandler = new DeletePlayerCommandHandler(repository);

    @Test
    void shouldDeletePlayer() {
        // Given
        repository.save(new Player("123", "Spoofy"));

        var command = new DeletePlayerCommand("123");

        // When
        commandHandler.handle(command);

        // Then
        var playerQuery = repository.findById(command.id());
        assertThat(playerQuery).isEmpty();
    }

    @Test
    void whenPlayerNotFound_shouldThrow() {
        // Given
        var command = new DeletePlayerCommand("123");

        // When
        // Then
        assertThatThrownBy(() -> commandHandler.handle(command))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("The entity Player with the id 123 was not found");
    }
}
