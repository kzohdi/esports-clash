package com.spoofy.esportclash.player.e2e;

import com.spoofy.esportclash.IntegrationTests;
import com.spoofy.esportclash.player.application.ports.PlayerRepository;
import com.spoofy.esportclash.player.domain.model.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DeletePlayerE2ETests extends IntegrationTests {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void shouldDeletePlayer() throws Exception {
        var existingPlayer = new Player("123", "name");
        playerRepository.save(existingPlayer);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/players/" + existingPlayer.getId())
                        .header("Authorization", createJwt()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        var playerQuery = playerRepository.findById(existingPlayer.getId());

        assertTrue(playerQuery.isEmpty());
    }

    @Test
    void whenPlayerNotFound_shouldFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/players/BadID")
                        .header("Authorization", createJwt()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
