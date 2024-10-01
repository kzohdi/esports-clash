package com.spoofy.esportclash.team.usecases;

import com.spoofy.esportclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportclash.team.application.ports.TeamRepository;
import com.spoofy.esportclash.team.application.usecases.DeleteTeamCommand;
import com.spoofy.esportclash.team.application.usecases.DeleteTeamCommandHandler;
import com.spoofy.esportclash.team.domain.model.Team;
import com.spoofy.esportclash.team.infrastucture.persistence.ram.InMemoryTeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteTeamTests {

    private final TeamRepository teamRepository = new InMemoryTeamRepository();

    private DeleteTeamCommandHandler createHandler() {
        return new DeleteTeamCommandHandler(teamRepository);
    }

    @BeforeEach
    void setup() {
        teamRepository.clear();
    }

    @Test
    void shouldCreateTeam() {
        var team = new Team("team1", "Team rocket");
        teamRepository.save(team);

        var command = new DeleteTeamCommand("team1");
        var commandHandler = createHandler();

        commandHandler.handle(command);

        var teamQuery = teamRepository.findById(command.getId());

        assertFalse(teamQuery.isPresent());
    }

    @Test
    void whenTeamDoesNotExist_shouldThrow() {
        var command = new DeleteTeamCommand("team1");
        var commandHandler = createHandler();

        var exception = assertThrows(NotFoundException.class, () ->
                commandHandler.handle(command));

        assertEquals("The entity Team with the id team1 was not found", exception.getMessage());
    }
}
