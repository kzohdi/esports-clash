package com.spoofy.esportsclash.team.e2e;

import com.spoofy.esportsclash.IntegrationTestBase;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.model.Player;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.domain.model.Role;
import com.spoofy.esportsclash.team.domain.model.Team;
import com.spoofy.esportsclash.team.infrastructure.spring.dto.AddPlayerToTeamDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AddPlayerToTeamE2ETests extends IntegrationTestBase {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void shouldAddPlayerToTeam() throws Exception {
        // Given
        var player = new Player("player1", "Spoofy");
        playerRepository.save(player);
        var team = new Team("team1", "Team rocket");
        teamRepository.save(team);

        var dto = new AddPlayerToTeamDTO(player.getId(), team.getId(), Role.TOP);

        // When
        mockMvc.perform(post("/teams/add-player-to-team")
                        .header(AUTHORIZATION_HEADER, createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        // Then
        var updatedTeam = teamRepository.findById(team.getId()).get();
        assertTrue(updatedTeam.hasMember(player.getId(), Role.TOP));
    }

    @Test
    void whenPlayerAlreadyInTeam_shouldThrow() throws Exception {
        // Given
        var player = new Player("player1", "Spoofy");
        playerRepository.save(player);

        var team1 = new Team("team1", "Team rocket 1");
        team1.addMember(player.getId(), Role.TOP);
        teamRepository.save(team1);

        var team2 = new Team("team2", "Team rocket 2");
        teamRepository.save(team2);

        var dto = new AddPlayerToTeamDTO(player.getId(), team2.getId(), Role.TOP);

        // When
        mockMvc.perform(post("/teams/add-player-to-team")
                        .header(AUTHORIZATION_HEADER, createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
