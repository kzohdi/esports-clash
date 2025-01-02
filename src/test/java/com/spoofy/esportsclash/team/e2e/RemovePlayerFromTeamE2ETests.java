package com.spoofy.esportsclash.team.e2e;

import com.spoofy.esportsclash.IntegrationTestTemplate;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.models.Player;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.domain.models.Role;
import com.spoofy.esportsclash.team.domain.models.Team;
import com.spoofy.esportsclash.team.infrastructure.spring.dtos.RemovePlayerFromTeamDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class RemovePlayerFromTeamE2ETests extends IntegrationTestTemplate {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void shouldRemovePlayerFromTeam() throws Exception {
        // Given
        var team = new Team("team1", "Team Spoofy");
        teamRepository.save(team);

        var player = new Player("player1", "Spoofy");
        playerRepository.save(player);

        team.addMember(player.getId(), Role.TOP);
        teamRepository.save(team);

        var dto = new RemovePlayerFromTeamDTO(player.getId(), team.getId());

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/teams/remove-player-from-team")
                        .header("Authorization", createJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Then
        var actualTeam = teamRepository.findById(team.getId()).get();

        assertThat(actualTeam.hasMember(player.getId())).isFalse();
    }
}
