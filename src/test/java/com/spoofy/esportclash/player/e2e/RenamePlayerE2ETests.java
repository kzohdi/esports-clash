package com.spoofy.esportclash.player.e2e;

import com.spoofy.esportclash.IntegrationTests;
import com.spoofy.esportclash.player.application.ports.PlayerRepository;
import com.spoofy.esportclash.player.domain.model.Player;
import com.spoofy.esportclash.player.infrastructure.spring.RenamePlayerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RenamePlayerE2ETests extends IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void shouldRenamePlayer() throws Exception {
        var existingPlayer = new Player("123", "old name");
        playerRepository.save(existingPlayer);

        var dto = new RenamePlayerDTO("new name");

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/players/" + existingPlayer.getId() + "/name")
                        .header("Authorization", createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        var player = playerRepository.findById(existingPlayer.getId()).get();

        assertEquals(dto.getName(), player.getName());
    }

    @Test
    void whenPlayerNotFound_shouldFail() throws Exception {
        var dto = new RenamePlayerDTO("new name");

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/players/BadID/name")
                        .header("Authorization", createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
