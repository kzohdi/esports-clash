package com.spoofy.esportclash.team.e2e;

import com.spoofy.esportclash.IntegrationTests;
import com.spoofy.esportclash.player.application.ports.PlayerRepository;
import com.spoofy.esportclash.player.domain.model.Player;
import com.spoofy.esportclash.team.application.ports.TeamRepository;
import com.spoofy.esportclash.team.domain.model.Role;
import com.spoofy.esportclash.team.domain.model.Team;
import com.spoofy.esportclash.team.domain.viewmodel.TeamViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetTeamByIdE2ETests extends IntegrationTests {

    Team team;
    Player player;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @BeforeEach
    void setup() {
        team = new Team("team1", "Team rocket");
        teamRepository.save(team);

        player = new Player("player1", "Spoofy");
        playerRepository.save(player);

        team.addMember(player.getId(), Role.TOP);
        teamRepository.save(team);

        clearDatabaseCache();
    }

    @Test
    void shouldGetTheTeam() throws Exception {
        var result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/teams/" + team.getId())
                        .header("Authorization", createJwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var viewModel = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TeamViewModel.class
        );

        assertEquals(team.getId(), viewModel.getId());
        assertEquals(team.getName(), viewModel.getName());

        var firstMember = viewModel.getMembers().get(0);
        assertEquals(player.getId(), firstMember.getPlayerId());
        assertEquals(player.getName(), firstMember.getPlayerName());
        assertEquals(Role.TOP.toString(), firstMember.getRole());
    }
}
