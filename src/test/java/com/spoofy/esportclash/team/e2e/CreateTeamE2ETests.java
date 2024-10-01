package com.spoofy.esportclash.team.e2e;

import com.spoofy.esportclash.IntegrationTests;
import com.spoofy.esportclash.core.domain.viewmodel.IdResponse;
import com.spoofy.esportclash.team.application.ports.TeamRepository;
import com.spoofy.esportclash.team.infrastucture.spring.dto.CreateTeamDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateTeamE2ETests extends IntegrationTests {

    @Autowired
    private TeamRepository teamRepository;

    @Test
    void shouldCreateTeam() throws Exception {
        var dto = new CreateTeamDTO("Team rocket");

        var result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/teams")
                        .header("Authorization", createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var idResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                IdResponse.class
        );

        var team = teamRepository.findById(idResponse.getId()).get();

        assertEquals(dto.getName(), team.getName());
    }
}
