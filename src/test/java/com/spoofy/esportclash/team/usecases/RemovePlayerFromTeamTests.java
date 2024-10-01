package com.spoofy.esportclash.team.usecases;

import com.spoofy.esportclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportclash.player.application.ports.PlayerRepository;
import com.spoofy.esportclash.player.domain.model.Player;
import com.spoofy.esportclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import com.spoofy.esportclash.team.application.ports.TeamRepository;
import com.spoofy.esportclash.team.application.usecases.RemovePlayerFromTeamCommand;
import com.spoofy.esportclash.team.application.usecases.RemovePlayerFromTeamCommandHandler;
import com.spoofy.esportclash.team.domain.model.Role;
import com.spoofy.esportclash.team.domain.model.Team;
import com.spoofy.esportclash.team.infrastucture.persistence.ram.InMemoryTeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemovePlayerFromTeamTests {

    private final TeamRepository teamRepository = new InMemoryTeamRepository();
    private final PlayerRepository playerRepository = new InMemoryPlayerRepository();

    private RemovePlayerFromTeamCommandHandler createHandler() {
        return new RemovePlayerFromTeamCommandHandler(teamRepository);
    }

    @BeforeEach
    void setup() {
        teamRepository.clear();
        playerRepository.clear();
    }

    @Test
    void shouldRemovePlayerFromTeam() {
        var team = new Team("team1", "Team rocket");
        teamRepository.save(team);

        var player = new Player("player1", "Spoofy");
        playerRepository.save(player);

        team.addMember(player.getId(), Role.TOP);
        teamRepository.save(team);

        var command = new RemovePlayerFromTeamCommand(team.getId(), player.getId());
        var commandHandler = createHandler();

        commandHandler.handle(command);

        var actualTeam = teamRepository.findById(team.getId()).get();

        assertFalse(actualTeam.hasMember(command.getPlayerId(), Role.TOP));
    }

    @Test
    void whenTeamDoesNotExist_shouldFail() {
        var player = new Player("player1", "Spoofy");
        playerRepository.save(player);

        var command = new RemovePlayerFromTeamCommand("team1", player.getId());
        var commandHandler = createHandler();

        var exception = assertThrows(NotFoundException.class, () ->
                commandHandler.handle(command));

        assertEquals("The entity Team with the id team1 was not found", exception.getMessage());
    }
}
