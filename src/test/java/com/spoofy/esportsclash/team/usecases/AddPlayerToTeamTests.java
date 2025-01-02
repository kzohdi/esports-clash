package com.spoofy.esportsclash.team.usecases;

import com.spoofy.esportsclash.core.domain.exceptions.BadRequestException;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.models.Player;
import com.spoofy.esportsclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.application.usecases.AddPlayerToTeamCommand;
import com.spoofy.esportsclash.team.application.usecases.AddPlayerToTeamCommandHandler;
import com.spoofy.esportsclash.team.domain.models.Role;
import com.spoofy.esportsclash.team.domain.models.Team;
import com.spoofy.esportsclash.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class AddPlayerToTeamTests {

    private final TeamRepository teamRepository = new InMemoryTeamRepository();
    private final PlayerRepository playerRepository = new InMemoryPlayerRepository();
    private final AddPlayerToTeamCommandHandler commandHandler = new AddPlayerToTeamCommandHandler(teamRepository, playerRepository);

    @Test
    void shouldAddPlayerToTeam() {
        // Given
        var player = new Player("player1", "Spoofy");
        playerRepository.save(player);

        var team = new Team("team1", "Spoofy Team");
        teamRepository.save(team);

        var command = new AddPlayerToTeamCommand(team.getId(), player.getId(), Role.TOP);

        // When
        commandHandler.handle(command);

        // Then
        var actualTeam = teamRepository.findById(team.getId()).get();
        assertThat(actualTeam.hasMember(player.getId(), Role.TOP)).isTrue();
    }

    @Test
    void whenTeamNotFound_shouldThrow() {
        // Given
        var player = new Player("player1", "Spoofy");
        playerRepository.save(player);

        var command = new AddPlayerToTeamCommand("team1", player.getId(), Role.TOP);

        // When
        // Then
        assertThatThrownBy(() -> commandHandler.handle(command))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("The entity Team with the id team1 was not found");
    }

    @Test
    void whenPlayerNotFound_shouldThrow() {
        // Given
        var team = new Team("team1", "Spoofy Team");
        teamRepository.save(team);

        var command = new AddPlayerToTeamCommand(team.getId(), "player1", Role.TOP);

        // When
        // Then
        assertThatThrownBy(() -> commandHandler.handle(command))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("The entity Player with the id player1 was not found");
    }

    @Test
    void whenPlayerInOtherTeam_shouldThrow() {
        // Given
        var team1 = new Team("team1", "Spoofy Team");
        teamRepository.save(team1);
        var player = new Player("player1", "Spoofy");
        playerRepository.save(player);
        team1.addMember(player.getId(), Role.TOP);
        teamRepository.save(team1);

        var team2 = new Team("team2", "Noob Team");
        teamRepository.save(team2);

        var command = new AddPlayerToTeamCommand(team2.getId(), player.getId(), Role.TOP);

        // When
        // Then
        assertThatThrownBy(() -> commandHandler.handle(command))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("This player is already in a team");

    }
}
