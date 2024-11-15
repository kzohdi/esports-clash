package com.spoofy.esportsclash.team.e2e;

import com.spoofy.esportsclash.IntegrationTestBase;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.model.Player;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.domain.model.Role;
import com.spoofy.esportsclash.team.domain.model.Team;
import com.spoofy.esportsclash.team.domain.viewmodel.TeamViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetTeamByIdE2ETests extends IntegrationTestBase {

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
    void shouldGetTeamById() throws Exception {
        // Given
        var player = new Player("player", "Spoofy");
        playerRepository.save(player);
        var team = new Team("team", "Team rocket");
        team.addMember(player.getId(), Role.TOP);
        teamRepository.save(team);

        // When
        var result = mockMvc.perform(get(String.format("/teams/%s", team.getId()))
                        .header(AUTHORIZATION_HEADER, createJwt()))
                .andExpect(status().isOk())
                .andReturn();

        var actualTeam = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TeamViewModel.class);

        // Then
        assertEquals(team.getId(), actualTeam.getId());
        assertEquals(team.getName(), actualTeam.getName());

        var firstMember = actualTeam.getMembers().getFirst();
        assertEquals(player.getId(), firstMember.getPlayerId());
        assertEquals(player.getName(), firstMember.getPlayerName());
        assertEquals(Role.TOP.name(), firstMember.getRole());
    }
}
