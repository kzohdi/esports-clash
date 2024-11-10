package com.spoofy.esportsclash.player.e2e;

import com.spoofy.esportsclash.PostgreSQLTestConfiguration;
import com.spoofy.esportsclash.player.application.port.PlayerRepository;
import com.spoofy.esportsclash.player.domain.model.Player;
import com.spoofy.esportsclash.player.infrastructure.spring.dto.RenamePlayerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgreSQLTestConfiguration.class)
public class RenamePlayerE2ETests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository playerRepository;

    @BeforeEach
    void setup() {
        playerRepository.clear();
    }

    @Test
    void shouldRenamePlayer() throws Exception {
        // Given
        var player = new Player("123", "Old Name");
        playerRepository.save(player);

        var renamePlayerDTO = new RenamePlayerDTO("New Name");

        // When
        mockMvc.perform(patch(String.format("/players/%s/name", player.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(renamePlayerDTO)))
                .andExpect(status().isOk());

        // Then
        var actualPlayer = playerRepository.findById(player.getId()).get();
        assertEquals(renamePlayerDTO.getName(), actualPlayer.getName());
    }

    @Test
    void whenPlayerNotFound_shouldThrow() throws Exception {
        // Given
        var garbageId = "123";
        var renamePlayerDTO = new RenamePlayerDTO("New Name");

        // When
        mockMvc.perform(patch(String.format("/players/%s/name", garbageId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(renamePlayerDTO)))
                .andExpect(status().isNotFound());
    }
}
