package com.spoofy.esportsclash.player.e2e;

import com.spoofy.esportsclash.IntegrationTestBase;
import com.spoofy.esportsclash.player.application.port.PlayerRepository;
import com.spoofy.esportsclash.player.domain.model.Player;
import com.spoofy.esportsclash.player.infrastructure.spring.dto.RenamePlayerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RenamePlayerE2ETests extends IntegrationTestBase {

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
                        .header(AUTHORIZATION_HEADER, createJwt())
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
                        .header(AUTHORIZATION_HEADER, createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(renamePlayerDTO)))
                .andExpect(status().isNotFound());
    }
}
