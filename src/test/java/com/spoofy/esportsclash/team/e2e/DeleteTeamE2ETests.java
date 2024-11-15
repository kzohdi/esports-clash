package com.spoofy.esportsclash.team.e2e;

import com.spoofy.esportsclash.IntegrationTestBase;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.domain.model.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteTeamE2ETests extends IntegrationTestBase {

    @Autowired
    private TeamRepository teamRepository;

    @Test
    void shouldDeleteTeam() throws Exception {
        // Given
        var team = new Team("team1", "Team rocket");
        teamRepository.save(team);

        // When
        mockMvc.perform(delete(String.format("/teams/%s", team.getId()))
                        .header(AUTHORIZATION_HEADER, createJwt()))
                .andExpect(status().isNoContent());

        // Then
        var teamQuery = teamRepository.findById(team.getId());
        assertFalse(teamQuery.isPresent());
    }

    @Test
    void whenTeamDoesNotExist_shouldThrow() throws Exception {
        // Given
        var garbageId = "123";

        // When
        // Then
        mockMvc.perform(delete(String.format("/teams/%s", garbageId))
                        .header(AUTHORIZATION_HEADER, createJwt()))
                .andExpect(status().isNotFound());
    }
}
