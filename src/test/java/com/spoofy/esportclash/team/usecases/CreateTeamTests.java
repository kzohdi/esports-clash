package com.spoofy.esportclash.team.usecases;

import com.spoofy.esportclash.team.application.ports.TeamRepository;
import com.spoofy.esportclash.team.application.usecases.CreateTeamCommand;
import com.spoofy.esportclash.team.application.usecases.CreateTeamCommandHandler;
import com.spoofy.esportclash.team.infrastucture.persistence.ram.InMemoryTeamRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateTeamTests {

    private final TeamRepository teamRepository = new InMemoryTeamRepository();

    private CreateTeamCommandHandler createHandler() {
        return new CreateTeamCommandHandler(teamRepository);
    }

    @Test
    void shouldCreateTeam() {
        var command = new CreateTeamCommand("Team rocket");
        var commandHandler = createHandler();

        var result = commandHandler.handle(command);

        var team = teamRepository.findById(result.getId()).get();

        assertEquals(command.getName(), team.getName());
    }
}
