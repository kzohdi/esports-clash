package com.spoofy.esportsclash.player.e2e;

import com.spoofy.esportsclash.IntegrationTestBase;
import com.spoofy.esportsclash.player.application.port.PlayerRepository;
import com.spoofy.esportsclash.player.domain.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeletePlayerE2ETests extends IntegrationTestBase {

    @Autowired
    private PlayerRepository playerRepository;

    @BeforeEach
    void setup() {
        playerRepository.clear();
    }

    @Test
    void shouldRenamePlayer() throws Exception {
        // Given
        var player = new Player("123", "Name");
        playerRepository.save(player);

        // When
        mockMvc.perform(delete(String.format("/players/%s", player.getId()))
                        .header(AUTHORIZATION_HEADER, createJwt()))
                .andExpect(status().isNoContent());

        // Then
        var playerQuery = playerRepository.findById(player.getId());
        assertFalse(playerQuery.isPresent());
    }

    @Test
    void whenPlayerNotFound_shouldThrow() throws Exception {
        // Given
        var garbageId = "123";

        // When
        // Then
        mockMvc.perform(delete(String.format("/players/%s", garbageId))
                        .header(AUTHORIZATION_HEADER, createJwt()))
                .andExpect(status().isNotFound());
    }
}
