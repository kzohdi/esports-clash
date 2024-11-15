package com.spoofy.esportsclash.team.usecases;

import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.application.usecases.CreateTeamCommand;
import com.spoofy.esportsclash.team.application.usecases.CreateTeamCommandHandler;
import com.spoofy.esportsclash.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateTeamTests {

    private final TeamRepository teamRepository = new InMemoryTeamRepository();

    private final CreateTeamCommandHandler commandHandler = new CreateTeamCommandHandler(teamRepository);

    @Test
    void shouldCreateTeam() {
        // Given
        var command = new CreateTeamCommand("Team rocket");

        // When
        var result = commandHandler.handle(command);

        var actualTeam = teamRepository.findById(result.getId()).get();

        // Then
        assertEquals(command.name(), actualTeam.getName());
    }
}
