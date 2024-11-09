package com.spoofy.esportsclash.player;

import com.spoofy.esportsclash.player.application.port.PlayerRepository;
import com.spoofy.esportsclash.player.application.usecases.CreatePlayerCommand;
import com.spoofy.esportsclash.player.application.usecases.CreatePlayerCommandHandler;
import com.spoofy.esportsclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreatePlayerTests {

    private PlayerRepository playerRepository;

    private CreatePlayerCommandHandler createPlayerCommandHandler;

    @BeforeEach
    void setup() {
        playerRepository = new InMemoryPlayerRepository();
        createPlayerCommandHandler = new CreatePlayerCommandHandler(playerRepository);
    }

    @Test
    void shouldCreatePlayer() {
        // Given
        var command = new CreatePlayerCommand("spoofy");

        // When
        var result = createPlayerCommandHandler.handle(command);

        var actualPlayer = playerRepository.findById(result.getId());

        // Then
        assertEquals(command.getName(), actualPlayer.getName());
    }
}
