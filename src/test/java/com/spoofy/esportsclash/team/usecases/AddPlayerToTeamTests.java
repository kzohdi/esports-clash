package com.spoofy.esportsclash.team.usecases;

import com.spoofy.esportsclash.core.domain.exceptions.BadRequestException;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.model.Player;
import com.spoofy.esportsclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.application.usecases.AddPlayerToTeamCommand;
import com.spoofy.esportsclash.team.application.usecases.AddPlayerToTeamCommandHandler;
import com.spoofy.esportsclash.team.domain.model.Role;
import com.spoofy.esportsclash.team.domain.model.Team;
import com.spoofy.esportsclash.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddPlayerToTeamTests {

    private final TeamRepository teamRepository = new InMemoryTeamRepository();

    private final PlayerRepository playerRepository = new InMemoryPlayerRepository();

    private final AddPlayerToTeamCommandHandler commandHandler = new AddPlayerToTeamCommandHandler(teamRepository, playerRepository);

    @BeforeEach
    void setup() {
        teamRepository.clear();
        playerRepository.clear();
    }

    @Test
    void shouldAddPlayerToTeam() {
        // Given
        var player = new Player("player1", "Spoofy");
        playerRepository.save(player);

        var team = new Team("team1", "Team rocket");
        teamRepository.save(team);

        var command = new AddPlayerToTeamCommand(player.getId(), team.getId(), Role.TOP);

        // When
        commandHandler.handle(command);

        var actualTeam = teamRepository.findById(team.getId()).get();

        // Then
        assertTrue(actualTeam.hasMember(player.getId(), Role.TOP));
    }

    @Test
    void whenPlayerDoesNotExist_shouldThrow() {
        // Given
        var team = new Team("team1", "Team rocket");
        teamRepository.save(team);

        var command = new AddPlayerToTeamCommand("player1", team.getId(), Role.TOP);

        // When
        var exception = assertThrows(NotFoundException.class,
                () -> commandHandler.handle(command));

        // Then
        assertEquals("Player with the key player1 not found", exception.getMessage());
    }

    @Test
    void whenTeamDoesNotExist_shouldThrow() {
        // Given
        var player = new Player("player1", "Spoofy");
        playerRepository.save(player);

        var command = new AddPlayerToTeamCommand(player.getId(), "team1", Role.TOP);

        // When
        var exception = assertThrows(NotFoundException.class,
                () -> commandHandler.handle(command));

        // Then
        assertEquals("Team with the key team1 not found", exception.getMessage());
    }

    @Test
    void whenPlayerAlreadyInTeam_shouldThrow() {
        // Given
        var player = new Player("player1", "Spoofy");
        playerRepository.save(player);

        var team1 = new Team("team1", "Team rocket 1");
        team1.addMember(player.getId(), Role.TOP);
        teamRepository.save(team1);

        var team2 = new Team("team2", "Team rocket 2");
        teamRepository.save(team2);

        var command = new AddPlayerToTeamCommand(player.getId(), team2.getId(), Role.TOP);

        // When
        var exception = assertThrows(BadRequestException.class,
                () -> commandHandler.handle(command));

        // Then
        assertEquals("Player player1 is already in a team", exception.getMessage());
    }
}
