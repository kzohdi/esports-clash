package com.spoofy.esportsclash.player.usecases;

import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.player.application.port.PlayerRepository;
import com.spoofy.esportsclash.player.application.usecases.DeletePlayerCommand;
import com.spoofy.esportsclash.player.application.usecases.DeletePlayerCommandHandler;
import com.spoofy.esportsclash.player.domain.model.Player;
import com.spoofy.esportsclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeletePlayerTests {

    private final PlayerRepository playerRepository = new InMemoryPlayerRepository();

    private final DeletePlayerCommandHandler deletePlayerCommandHandler = new DeletePlayerCommandHandler(playerRepository);

    @Test
    void shouldRenamePlayer() {
        // Given
        var existingPlayer = new Player("123", "Name");
        playerRepository.save(existingPlayer);

        var command = new DeletePlayerCommand(existingPlayer.getId());

        // When
        deletePlayerCommandHandler.handle(command);

        var playerQuery = playerRepository.findById(existingPlayer.getId());

        // Then
        assertFalse(playerQuery.isPresent());
    }

    @Test
    void whenPlayerNotFound_shouldThrow() {
        // Given
        var command = new DeletePlayerCommand("123");

        // When
        var exception = assertThrows(NotFoundException.class,
                () -> deletePlayerCommandHandler.handle(command));

        assertEquals("Player with the key 123 not found", exception.getMessage());
    }
}