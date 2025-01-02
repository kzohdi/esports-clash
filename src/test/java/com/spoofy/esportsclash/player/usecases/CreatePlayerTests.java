package com.spoofy.esportsclash.player.usecases;

import com.spoofy.esportsclash.player.application.usecases.CreatePlayerCommandHandler;
import com.spoofy.esportsclash.player.application.usecases.CreatePlayerCommand;
import com.spoofy.esportsclash.player.domain.models.Player;
import com.spoofy.esportsclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreatePlayerTests {

    @Test
    void shouldCreatePlayer() {
        // Given
        var repository = new InMemoryPlayerRepository();
        var commandHandler = new CreatePlayerCommandHandler(repository);

        var command = new CreatePlayerCommand("Spoofy");

        // When
        var result = commandHandler.handle(command);

        // Then
        var expected = new Player(result.id(), command.name());
        var actual = repository.findById(result.id()).get();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
