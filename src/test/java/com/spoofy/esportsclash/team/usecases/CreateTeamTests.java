package com.spoofy.esportsclash.team.usecases;

import com.spoofy.esportsclash.team.application.usecases.CreateTeamCommand;
import com.spoofy.esportsclash.team.application.usecases.CreateTeamCommandHandler;
import com.spoofy.esportsclash.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CreateTeamTests {

    @Test
    void shouldCreateTeam() {
        // Given
        var repository = new InMemoryTeamRepository();
        var commandHandler = new CreateTeamCommandHandler(repository);
        var command = new CreateTeamCommand("Team Spoofy");

        // When
        var result = commandHandler.handle(command);

        // Then
        var actualTeam = repository.findById(result.id()).get();
        assertThat(actualTeam.getName()).isEqualTo(command.name());
    }
}
