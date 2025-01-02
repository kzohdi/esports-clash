package com.spoofy.esportsclash.player.usecases;

import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.application.usecases.RenamePlayerCommand;
import com.spoofy.esportsclash.player.application.usecases.RenamePlayerCommandHandler;
import com.spoofy.esportsclash.player.domain.models.Player;
import com.spoofy.esportsclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RenamePlayerTests {

    private final PlayerRepository repository = new InMemoryPlayerRepository();
    private final RenamePlayerCommandHandler commandHandler = new RenamePlayerCommandHandler(repository);

    @Test
    void shouldCreatePlayer() {
        // Given
        repository.save(new Player("123", "Old Name"));

        var command = new RenamePlayerCommand("123", "New Name");

        // When
        commandHandler.handle(command);

        // Then
        var expected = new Player(command.id(), command.newName());
        var actual = repository.findById(command.id()).get();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void whenPlayerNotFound_shouldThrow() {
        // Given
        var command = new RenamePlayerCommand("123", "New Name");

        // When
        assertThatThrownBy(() -> commandHandler.handle(command))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("The entity Player with the id 123 was not found");
    }
}
