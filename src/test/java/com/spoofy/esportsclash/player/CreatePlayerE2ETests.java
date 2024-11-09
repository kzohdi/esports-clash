package com.spoofy.esportsclash.player;

import com.spoofy.esportsclash.PostgreSQLTestConfiguration;
import com.spoofy.esportsclash.player.application.port.PlayerRepository;
import com.spoofy.esportsclash.player.domain.viewmodel.IdResponse;
import com.spoofy.esportsclash.player.infrastructure.spring.dto.CreatePlayerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgreSQLTestConfiguration.class)
public class CreatePlayerE2ETests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void shouldCreatePlayer() throws Exception {
        // Given
        var createPlayerDTO = new CreatePlayerDTO("spoofy");

        // When
        var result = mockMvc.perform(post("/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPlayerDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        var idResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                IdResponse.class);

        // Then
        var actualPlayer = playerRepository.findById(idResponse.getId());
        assertEquals(createPlayerDTO.getName(), actualPlayer.getName());
    }
}
