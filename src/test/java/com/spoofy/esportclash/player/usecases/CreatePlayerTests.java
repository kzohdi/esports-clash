package com.spoofy.esportclash.player.usecases;

import com.spoofy.esportclash.player.application.usecases.CreatePlayerCommand;
import com.spoofy.esportclash.player.application.usecases.CreatePlayerCommandHandler;
import com.spoofy.esportclash.player.domain.model.Player;
import com.spoofy.esportclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreatePlayerTests {

    @Test
    void shouldCreatePlayer() {
        var repository = new InMemoryPlayerRepository();
        var commandHandler = new CreatePlayerCommandHandler(repository);

        var result = commandHandler.handle(new CreatePlayerCommand("name"));

        var expectedPlayer = new Player(result.getId(), "name");

        Player actualPlayer = repository.findById(expectedPlayer.getId()).get();

        assertEquals(expectedPlayer.getName(), actualPlayer.getName());
    }
}
