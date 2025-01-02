package com.spoofy.esportsclash.team.usecases;

import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.models.Player;
import com.spoofy.esportsclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.application.usecases.RemovePlayerFromTeamCommand;
import com.spoofy.esportsclash.team.application.usecases.RemovePlayerFromTeamCommandHandler;
import com.spoofy.esportsclash.team.domain.models.Role;
import com.spoofy.esportsclash.team.domain.models.Team;
import com.spoofy.esportsclash.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class RemovePlayerFromTeamTests {

    private final TeamRepository teamRepository = new InMemoryTeamRepository();
    private final PlayerRepository playerRepository = new InMemoryPlayerRepository();
    private final RemovePlayerFromTeamCommandHandler commandHandler = new RemovePlayerFromTeamCommandHandler(teamRepository, playerRepository);

    @Test
    void shouldRemovePlayerFromTeam() {
        // Given
        var player = new Player("player1", "Spoofy");
        playerRepository.save(player);

        var team = new Team("team1", "Spoofy Team");
        teamRepository.save(team);
        team.addMember(player.getId(), Role.TOP);

        var command = new RemovePlayerFromTeamCommand(team.getId(), player.getId());

        // When
        commandHandler.handle(command);

        // Then
        var actualTeam = teamRepository.findById(team.getId()).get();
        assertThat(actualTeam.hasMember(player.getId(), Role.TOP)).isFalse();
    }

    @Test
    void whenTeamNotFound_shouldThrow() {
        // Given
        var player = new Player("player1", "Spoofy");
        playerRepository.save(player);

        var command = new RemovePlayerFromTeamCommand("team1", player.getId());

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

        var command = new RemovePlayerFromTeamCommand(team.getId(), "player1");

        // When
        // Then
        assertThatThrownBy(() -> commandHandler.handle(command))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("The entity Player with the id player1 was not found");
    }
}
