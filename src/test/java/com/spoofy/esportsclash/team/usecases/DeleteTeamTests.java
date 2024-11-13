package com.spoofy.esportsclash.team.usecases;

import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.application.usecases.DeleteTeamCommand;
import com.spoofy.esportsclash.team.application.usecases.DeleteTeamCommandHandler;
import com.spoofy.esportsclash.team.domain.model.Team;
import com.spoofy.esportsclash.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteTeamTests {

    private final TeamRepository teamRepository = new InMemoryTeamRepository();

    private final DeleteTeamCommandHandler commandHandler = new DeleteTeamCommandHandler(teamRepository);

    @BeforeEach
    void setup() {
        teamRepository.clear();
    }

    @Test
    void shouldDeleteTeam() {
        // Given
        var team = new Team("team1", "Team rocket");
        teamRepository.save(team);

        var command = new DeleteTeamCommand(team.getId());

        // When
        commandHandler.handle(command);

        var teamQuery = teamRepository.findById(team.getId());

        // Then
        assertTrue(teamQuery.isEmpty());
    }

    @Test
    void whenTeamDoesNotExist_shouldThrow() {
        // Given
        var command = new DeleteTeamCommand("team1");

        // When
        var exception = assertThrows(NotFoundException.class, () ->
                commandHandler.handle(command));

        // Then
        assertEquals("Team with the key team1 not found", exception.getMessage());
    }
}
