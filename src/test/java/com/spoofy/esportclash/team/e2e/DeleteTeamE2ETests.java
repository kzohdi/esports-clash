package com.spoofy.esportclash.team.e2e;

import com.spoofy.esportclash.IntegrationTests;
import com.spoofy.esportclash.team.application.ports.TeamRepository;
import com.spoofy.esportclash.team.domain.model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertFalse;

class DeleteTeamE2ETests extends IntegrationTests {

    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void setup() {
        teamRepository.clear();
    }

    @Test
    void shouldDeleteTeam() throws Exception {
        var team = new Team("team1", "Team rocket");
        teamRepository.save(team);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/teams/" + team.getId())
                        .header("Authorization", createJwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        var teamQuery = teamRepository.findById(team.getId());

        assertFalse(teamQuery.isPresent());
    }
}
