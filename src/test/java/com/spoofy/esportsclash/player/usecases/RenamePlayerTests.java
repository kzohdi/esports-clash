package com.spoofy.esportsclash.player.usecases;

import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.application.usecases.RenamePlayerCommand;
import com.spoofy.esportsclash.player.application.usecases.RenamePlayerCommandHandler;
import com.spoofy.esportsclash.player.domain.model.Player;
import com.spoofy.esportsclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RenamePlayerTests {

    private final PlayerRepository playerRepository = new InMemoryPlayerRepository();

    private final RenamePlayerCommandHandler commandHandler = new RenamePlayerCommandHandler(playerRepository);

    @Test
    void shouldRenamePlayer() {
        // Given
        var existingPlayer = new Player("123", "Old Name");
        playerRepository.save(existingPlayer);

        var command = new RenamePlayerCommand(existingPlayer.getId(), "New Name");

        // When
        commandHandler.handle(command);

        var actualPlayer = playerRepository.findById(existingPlayer.getId()).get();

        // Then
        assertEquals(command.name(), actualPlayer.getName());
    }

    @Test
    void whenPlayerNotFound_shouldThrow() {
        // Given
        var command = new RenamePlayerCommand("123", "New Name");

        // When
        var exception = assertThrows(NotFoundException.class,
                () -> commandHandler.handle(command));

        assertEquals("Player with the key 123 not found", exception.getMessage());
    }
}
