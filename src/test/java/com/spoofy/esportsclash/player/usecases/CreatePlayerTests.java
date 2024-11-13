package com.spoofy.esportsclash.player.usecases;

import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.application.usecases.CreatePlayerCommand;
import com.spoofy.esportsclash.player.application.usecases.CreatePlayerCommandHandler;
import com.spoofy.esportsclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreatePlayerTests {

    private final PlayerRepository playerRepository = new InMemoryPlayerRepository();

    private final CreatePlayerCommandHandler commandHandler = new CreatePlayerCommandHandler(playerRepository);

    @Test
    void shouldCreatePlayer() {
        // Given
        var command = new CreatePlayerCommand("Name");

        // When
        var result = commandHandler.handle(command);

        var actualPlayer = playerRepository.findById(result.getId()).get();

        // Then
        assertEquals(command.name(), actualPlayer.getName());
    }
}
