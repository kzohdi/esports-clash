package com.spoofy.esportclash.player;

import com.spoofy.esportclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportclash.player.application.ports.PlayerRepository;
import com.spoofy.esportclash.player.application.usecases.GetPlayerByIdCommand;
import com.spoofy.esportclash.player.application.usecases.GetPlayerByIdCommandHandler;
import com.spoofy.esportclash.player.domain.model.Player;
import com.spoofy.esportclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetPlayerByIdTests {

    private final PlayerRepository repository = new InMemoryPlayerRepository();

    private GetPlayerByIdCommandHandler createHandler() {
        return new GetPlayerByIdCommandHandler(repository);
    }

    @Test
    void shouldGetPlayerById() {
        var player = new Player("123", "name");
        repository.save(player);

        var commandHandler = createHandler();
        var command = new GetPlayerByIdCommand(player.getId());
        var returnPlayer = commandHandler.handle(command);

        assertEquals(player.getName(), returnPlayer.getName());
    }

    @Test
    void whenPlayerDoesNotExist_shouldFail() {
        var command = new GetPlayerByIdCommand("BadID");
        var commandHandler = createHandler();

        var exception = assertThrows(NotFoundException.class, () ->
                commandHandler.handle(command)
        );

        assertEquals("The entity Player with the id BadID was not found", exception.getMessage());
    }


}
