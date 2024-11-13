package com.spoofy.esportsclash.player.e2e;

import com.spoofy.esportsclash.IntegrationTestBase;
import com.spoofy.esportsclash.core.domain.viewmodel.IdResponse;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.infrastructure.spring.dto.CreatePlayerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreatePlayerE2ETests extends IntegrationTestBase {

    @Autowired
    private PlayerRepository playerRepository;

    @BeforeEach
    void setup() {
        playerRepository.clear();
    }

    @Test
    void shouldCreatePlayer() throws Exception {
        // Given
        var createPlayerDTO = new CreatePlayerDTO("Name");

        // When
        var result = mockMvc.perform(post("/players")
                        .header(AUTHORIZATION_HEADER, createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPlayerDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        var idResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                IdResponse.class);

        // Then
        var actualPlayer = playerRepository.findById(idResponse.getId()).get();
        assertEquals(createPlayerDTO.getName(), actualPlayer.getName());
    }
}
