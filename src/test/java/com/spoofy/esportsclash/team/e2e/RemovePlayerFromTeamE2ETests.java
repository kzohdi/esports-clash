package com.spoofy.esportsclash.team.e2e;

import com.spoofy.esportsclash.IntegrationTestBase;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.model.Player;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.domain.model.Role;
import com.spoofy.esportsclash.team.domain.model.Team;
import com.spoofy.esportsclash.team.infrastructure.spring.dto.RemovePlayerFromTeamDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RemovePlayerFromTeamE2ETests extends IntegrationTestBase {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void shouldAddPlayerToTeam() throws Exception {
        // Given
        var player = new Player("player1", "Spoofy");
        var team = new Team("team1", "Team rocket");
        team.addMember(player.getId(), Role.TOP);
        playerRepository.save(player);
        teamRepository.save(team);


        var dto = new RemovePlayerFromTeamDTO(player.getId(), team.getId());

        // When
        mockMvc.perform(post("/teams/remove-player-from-team")
                        .header(AUTHORIZATION_HEADER, createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        // Then
        var updatedTeam = teamRepository.findById(team.getId()).get();
        assertFalse(updatedTeam.hasMember(player.getId()));
    }
}
