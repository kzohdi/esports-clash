package com.spoofy.esportsclash.team.e2e;

import com.spoofy.esportsclash.IntegrationTestBase;
import com.spoofy.esportsclash.core.domain.viewmodel.IdResponse;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.infrastructure.spring.dto.CreateTeamDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateTeamE2ETests extends IntegrationTestBase {

    @Autowired
    private TeamRepository teamRepository;

    @Test
    void shouldCreateTeam() throws Exception {
        // Given
        var createTeamDTO = new CreateTeamDTO("Team rocket");

        // When
        var result = mockMvc.perform(post("/teams")
                        .header(AUTHORIZATION_HEADER, createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTeamDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        var idResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                IdResponse.class);

        // Then
        var actualTeam = teamRepository.findById(idResponse.getId()).get();
        assertEquals(createTeamDTO.getName(), actualTeam.getName());
    }
}
