package com.spoofy.esportsclash.team.e2e;

import com.spoofy.esportsclash.IntegrationTestTemplate;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.models.Player;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.domain.models.Role;
import com.spoofy.esportsclash.team.domain.models.Team;
import com.spoofy.esportsclash.team.domain.viewmodels.TeamViewModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class GetTeamByIdE2ETests extends IntegrationTestTemplate {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void shouldGetTeamById() throws Exception {
        // Given
        var team = new Team("team1", "Team Spoofy");
        teamRepository.save(team);

        var player = new Player("player1", "Spoofy");
        playerRepository.save(player);

        team.addMember(player.getId(), Role.TOP);
        teamRepository.save(team);

        clearDatabaseCache();

        // When
        var result = mockMvc.perform(MockMvcRequestBuilders.get("/teams/team1")
                        .header("Authorization", createJwtToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TeamViewModel.class
        );

        // Then
        assertThat(response.id()).isEqualTo(team.getId());
        assertThat(response.name()).isEqualTo(team.getName());

        var firstMember = response.members().getFirst();

        assertThat(firstMember.playerId()).isEqualTo(player.getId());
        assertThat(firstMember.playerName()).isEqualTo(player.getName());
        assertThat(firstMember.role()).isEqualTo(Role.TOP.name());
    }
}
