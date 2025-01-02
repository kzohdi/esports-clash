package com.spoofy.esportsclash.team.e2e;

import com.spoofy.esportsclash.IntegrationTestTemplate;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.models.Player;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.domain.models.Role;
import com.spoofy.esportsclash.team.domain.models.Team;
import com.spoofy.esportsclash.team.infrastructure.spring.dtos.AddPlayerToTeamDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class AddPlayerToTeamE2ETests extends IntegrationTestTemplate {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void shouldAddPlayerToTeam() throws Exception {
        // Given
        var team = new Team("team1", "Team Spoofy");
        teamRepository.save(team);

        var player = new Player("player1", "Spoofy");
        playerRepository.save(player);

        var dto = new AddPlayerToTeamDTO(player.getId(), team.getId(), "TOP");

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/teams/add-player-to-team")
                        .header("Authorization", createJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Then
        var actualTeam = teamRepository.findById(team.getId()).get();

        assertThat(actualTeam.hasMember(player.getId(), Role.TOP)).isTrue();
    }

    @Test
    void whenPlayerIsAlreadyInAnotherTeam_shouldThrow() throws Exception {
        // Given
        var team1 = new Team("team1", "Team Spoofy");
        teamRepository.save(team1);

        var player = new Player("player1", "Spoofy");
        playerRepository.save(player);

        team1.addMember(player.getId(), Role.TOP);
        teamRepository.save(team1);

        var team2 = new Team("team2", "Team Noobs");
        teamRepository.save(team2);

        var dto = new AddPlayerToTeamDTO(player.getId(), team2.getId(), "TOP");

        // When
        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/teams/add-player-to-team")
                        .header("Authorization", createJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
}
