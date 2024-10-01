package com.spoofy.esportclash.player.usecases;

import com.spoofy.esportclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportclash.player.application.ports.PlayerRepository;
import com.spoofy.esportclash.player.application.usecases.RenamePlayerCommand;
import com.spoofy.esportclash.player.application.usecases.RenamePlayerCommandHandler;
import com.spoofy.esportclash.player.domain.model.Player;
import com.spoofy.esportclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RenamePlayerTests {

    private final PlayerRepository repository = new InMemoryPlayerRepository();

    private RenamePlayerCommandHandler createHandler() {
        return new RenamePlayerCommandHandler(repository);
    }

    @Test
    void shouldRenamePlayer() {
        var player = new Player("123", "old name");
        repository.save(player);

        var command = new RenamePlayerCommand(player.getId(), "new name");
        var commandHandler = createHandler();
        commandHandler.handle(command);

        var renamedPlayer = repository.findById(player.getId()).get();

        assertEquals("new name", renamedPlayer.getName());
    }

    @Test
    void whenPlayerDoesNotExist_shouldFail() {
        var command = new RenamePlayerCommand("BadID", "new name");
        var commandHandler = createHandler();

        var exception = assertThrows(NotFoundException.class, () ->
                commandHandler.handle(command)
        );

        assertEquals("The entity Player with the id BadID was not found", exception.getMessage());
    }

}
