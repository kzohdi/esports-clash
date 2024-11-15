package com.spoofy.esportsclash.team.usecases;

import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.application.usecases.RemovePlayerFromTeamCommand;
import com.spoofy.esportsclash.team.application.usecases.RemovePlayerFromTeamCommandHandler;
import com.spoofy.esportsclash.team.domain.model.Role;
import com.spoofy.esportsclash.team.domain.model.Team;
import com.spoofy.esportsclash.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemovePlayerFromTeamTests {

    private final TeamRepository teamRepository = new InMemoryTeamRepository();

    private final RemovePlayerFromTeamCommandHandler commandHandler = new RemovePlayerFromTeamCommandHandler(teamRepository);

    @BeforeEach
    void setup() {
        teamRepository.clear();
    }

    @Test
    void shouldRemovePlayerFromTeam() {
        // Given
        var team = new Team("team1", "Team rocket");
        team.addMember("player1", Role.TOP);
        teamRepository.save(team);

        var command = new RemovePlayerFromTeamCommand("player1", team.getId());

        // When
        commandHandler.handle(command);

        var actualTeam = teamRepository.findById(team.getId()).get();

        // Then
        assertFalse(actualTeam.hasMember("player1"));
    }

    @Test
    void whenTeamDoesNotExist_shouldThrow() {
        // Given
        var command = new RemovePlayerFromTeamCommand("player1", "team1");

        // When
        var exception = assertThrows(NotFoundException.class,
                () -> commandHandler.handle(command));

        // Then
        assertEquals("Team with the key team1 not found", exception.getMessage());
    }
}
