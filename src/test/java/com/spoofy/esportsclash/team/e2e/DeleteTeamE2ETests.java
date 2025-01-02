package com.spoofy.esportsclash.team.e2e;

import com.spoofy.esportsclash.IntegrationTestTemplate;
import com.spoofy.esportsclash.core.domain.viewmodels.IdResponse;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.domain.models.Team;
import com.spoofy.esportsclash.team.infrastructure.spring.dtos.CreateTeamDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class DeleteTeamE2ETests extends IntegrationTestTemplate {

    @Autowired
    private TeamRepository teamRepository;

    @Test
    void shouldDeleteTeam() throws Exception {
        // Given
        var team = new Team("123", "Spoofy Team");
        teamRepository.save(team);

        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/teams/123")
                        .header("Authorization", createJwtToken()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        // Then
        var teamQuery = teamRepository.findById(team.getId());

        assertThat(teamQuery).isEmpty();
    }
}
