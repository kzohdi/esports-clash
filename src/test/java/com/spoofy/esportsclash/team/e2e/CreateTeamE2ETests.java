package com.spoofy.esportsclash.team.e2e;

import com.spoofy.esportsclash.IntegrationTestTemplate;
import com.spoofy.esportsclash.core.domain.viewmodels.IdResponse;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.models.Player;
import com.spoofy.esportsclash.player.infrastructure.spring.dtos.CreatePlayerDTO;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.domain.models.Team;
import com.spoofy.esportsclash.team.infrastructure.spring.dtos.CreateTeamDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class CreateTeamE2ETests extends IntegrationTestTemplate {

    @Autowired
    private TeamRepository teamRepository;

    @Test
    void shouldCreateTeam() throws Exception {
        // Given
        var dto = new CreateTeamDTO("Team Spoofy");

        // When
        var result = mockMvc.perform(MockMvcRequestBuilders.post("/teams")
                        .header("Authorization", createJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                IdResponse.class
        );

        // Then
        var expected = new Team(response.id(), dto.name());
        var actual = teamRepository.findById(response.id()).get();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
