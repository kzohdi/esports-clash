package com.spoofy.esportclash.player.usecases;

import com.spoofy.esportclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportclash.player.application.ports.PlayerRepository;
import com.spoofy.esportclash.player.application.usecases.DeletePlayerCommand;
import com.spoofy.esportclash.player.application.usecases.DeletePlayerCommandHandler;
import com.spoofy.esportclash.player.domain.model.Player;
import com.spoofy.esportclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeletePlayerTests {

    private final PlayerRepository repository = new InMemoryPlayerRepository();

    private DeletePlayerCommandHandler createHandler() {
        return new DeletePlayerCommandHandler(repository);
    }

    @Test
    void shouldDeletePlayer() {
        var player = new Player("123", "name");
        repository.save(player);

        var command = new DeletePlayerCommand(player.getId());
        var commandHandler = createHandler();
        commandHandler.handle(command);

        var deletedPlayerQuery = repository.findById(player.getId());

        assertTrue(deletedPlayerQuery.isEmpty());
    }

    @Test
    void whenPlayerDoesNotExist_shouldFail() {
        var command = new DeletePlayerCommand("BadID");
        var commandHandler = createHandler();

        var exception = assertThrows(NotFoundException.class, () ->
                commandHandler.handle(command)
        );

        assertEquals("The entity Player with the id BadID was not found", exception.getMessage());
    }

}
