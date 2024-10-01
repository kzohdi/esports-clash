package com.spoofy.esportclash.team.e2e;

import com.spoofy.esportclash.IntegrationTests;
import com.spoofy.esportclash.player.application.ports.PlayerRepository;
import com.spoofy.esportclash.player.domain.model.Player;
import com.spoofy.esportclash.team.application.ports.TeamRepository;
import com.spoofy.esportclash.team.domain.model.Role;
import com.spoofy.esportclash.team.domain.model.Team;
import com.spoofy.esportclash.team.infrastucture.spring.dto.RemovePlayerFromTeamDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertFalse;

class RemovePlayerFromTeamE2ETests extends IntegrationTests {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @BeforeEach
    void setup() {
        teamRepository.clear();
        playerRepository.clear();
    }

    @Test
    void shouldRemovePlayerFromTeam() throws Exception {
        var team = new Team("team1", "Team rocket");
        teamRepository.save(team);

        var player = new Player("player1", "Spoofy");
        playerRepository.save(player);

        team.addMember(player.getId(), Role.TOP);
        teamRepository.save(team);

        var dto = new RemovePlayerFromTeamDTO(team.getId(), player.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/teams/remove-player-from-team")
                        .header("Authorization", createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        var updatedTeam = teamRepository.findById(team.getId()).get();

        assertFalse(updatedTeam.hasMember(player.getId(), Role.TOP));
    }
}
