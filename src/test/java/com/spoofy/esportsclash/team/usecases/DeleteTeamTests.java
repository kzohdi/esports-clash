package com.spoofy.esportsclash.team.usecases;

import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.application.usecases.DeleteTeamCommand;
import com.spoofy.esportsclash.team.application.usecases.DeleteTeamCommandHandler;
import com.spoofy.esportsclash.team.domain.models.Team;
import com.spoofy.esportsclash.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class DeleteTeamTests {

    private final TeamRepository repository = new InMemoryTeamRepository();
    private final DeleteTeamCommandHandler commandHandler = new DeleteTeamCommandHandler(repository);

    @Test
    void shouldDeleteTeam() {
        // Given
        var team = new Team("123", "Team Spoofy");
        repository.save(team);

        var command = new DeleteTeamCommand(team.getId());

        // When
        commandHandler.handle(command);

        // Then
        var teamQuery = repository.findById(team.getId());
        assertThat(teamQuery).isEmpty();
    }

    @Test
    void whenTeamNotFound_shouldThrowException() {
        // Given
        var command = new DeleteTeamCommand("123");

        // When
        assertThatThrownBy(() -> commandHandler.handle(command))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("The entity Team with the id 123 was not found");
    }
}
