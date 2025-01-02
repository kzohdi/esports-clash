package com.spoofy.esportsclash.player.e2e;

import com.spoofy.esportsclash.IntegrationTestTemplate;
import com.spoofy.esportsclash.core.domain.viewmodels.IdResponse;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.models.Player;
import com.spoofy.esportsclash.player.infrastructure.spring.dtos.CreatePlayerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class CreatePlayerE2ETests extends IntegrationTestTemplate {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void shouldCreatePlayer() throws Exception {
        // Given
        var dto = new CreatePlayerDTO("Spoofy");

        // When
        var result = mockMvc.perform(MockMvcRequestBuilders.post("/players")
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
        var expected = new Player(response.id(), dto.name());
        var actual = playerRepository.findById(response.id()).get();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
