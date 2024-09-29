package com.spoofy.esportclash.player;

import com.spoofy.esportclash.PostgreSQLTestConfiguration;
import com.spoofy.esportclash.player.application.ports.PlayerRepository;
import com.spoofy.esportclash.player.domain.model.Player;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgreSQLTestConfiguration.class)
@Transactional
class DeletePlayerE2ETests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository repository;

    @Test
    void shouldDeletePlayer() throws Exception {
        var existingPlayer = new Player("123", "name");
        repository.save(existingPlayer);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/players/" + existingPlayer.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        var playerQuery = repository.findById(existingPlayer.getId());

        assertTrue(playerQuery.isEmpty());
    }

    @Test
    void whenPlayerNotFound_shouldFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/players/BadID"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
