package com.spoofy.esportclash.team.usecases;

import com.spoofy.esportclash.core.domain.exceptions.BadRequestException;
import com.spoofy.esportclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportclash.player.application.ports.PlayerRepository;
import com.spoofy.esportclash.player.domain.model.Player;
import com.spoofy.esportclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import com.spoofy.esportclash.team.application.ports.TeamRepository;
import com.spoofy.esportclash.team.application.usecases.AddPlayerToTeamCommand;
import com.spoofy.esportclash.team.application.usecases.AddPlayerToTeamCommandHandler;
import com.spoofy.esportclash.team.domain.model.Role;
import com.spoofy.esportclash.team.domain.model.Team;
import com.spoofy.esportclash.team.infrastucture.persistence.ram.InMemoryTeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddPlayerToTeamTests {

    private final TeamRepository teamRepository = new InMemoryTeamRepository();
    private final PlayerRepository playerRepository = new InMemoryPlayerRepository();

    private AddPlayerToTeamCommandHandler createHandler() {
        return new AddPlayerToTeamCommandHandler(teamRepository, playerRepository);
    }

    @BeforeEach
    void setup() {
        teamRepository.clear();
        playerRepository.clear();
    }

    @Test
    void shouldAddPlayerToTeam() {
        var team = new Team("team1", "Team rocket");
        teamRepository.save(team);

        var player = new Player("player1", "Spoofy");
        playerRepository.save(player);

        var command = new AddPlayerToTeamCommand(team.getId(), player.getId(), Role.TOP);
        var commandHandler = createHandler();

        commandHandler.handle(command);

        var actualTeam = teamRepository.findById(team.getId()).get();

        assertTrue(actualTeam.hasMember(command.getPlayerId(), command.getRole()));
    }

    @Test
    void whenTeamDoesNotExist_shouldFail() {
        var player = new Player("player1", "Spoofy");
        playerRepository.save(player);

        var command = new AddPlayerToTeamCommand("team1", player.getId(), Role.TOP);
        var commandHandler = createHandler();

        var exception = assertThrows(NotFoundException.class, () ->
                commandHandler.handle(command));

        assertEquals("The entity Team with the id team1 was not found", exception.getMessage());
    }

    @Test
    void whenPlayerDoesNotExist_shouldFail() {
        var team = new Team("team1", "Team rocket");
        teamRepository.save(team);

        var command = new AddPlayerToTeamCommand(team.getId(), "player1", Role.TOP);
        var commandHandler = createHandler();

        var exception = assertThrows(NotFoundException.class, () ->
                commandHandler.handle(command));

        assertEquals("The entity Player with the id player1 was not found", exception.getMessage());
    }

    @Test
    void whenPlayerIsAlreadyInAnotherTeam_shouldFail() {
        var team1 = new Team("team1", "Team rocket");
        teamRepository.save(team1);

        var player = new Player("player1", "Spoofy");
        playerRepository.save(player);

        team1.addMember(player.getId(), Role.TOP);
        teamRepository.save(team1);

        var team2 = new Team("team2", "Team noobs");
        teamRepository.save(team2);

        var command = new AddPlayerToTeamCommand(team2.getId(), player.getId(), Role.TOP);
        var commandHandler = createHandler();

        var exception = assertThrows(BadRequestException.class, () ->
                commandHandler.handle(command));

        assertEquals("Player player1 is already in another team", exception.getMessage());
    }
}
