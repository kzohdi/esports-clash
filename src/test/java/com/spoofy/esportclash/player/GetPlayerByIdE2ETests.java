package com.spoofy.esportclash.player;

import com.spoofy.esportclash.PostgreSQLTestConfiguration;
import com.spoofy.esportclash.player.application.ports.PlayerRepository;
import com.spoofy.esportclash.player.domain.model.Player;
import com.spoofy.esportclash.player.domain.viewmodel.PlayerViewModel;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgreSQLTestConfiguration.class)
@Transactional
class GetPlayerByIdE2ETests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository repository;

    @Test
    void shouldGetPlayerById() throws Exception {
        var existingPlayer = new Player("123", "name");
        repository.save(existingPlayer);

        var result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/players/" + existingPlayer.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var actualPlayer = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                PlayerViewModel.class
        );

        assertEquals(existingPlayer.getName(), actualPlayer.getName());
    }

    @Test
    void whenPlayerNotFound_shouldFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/players/BadID"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
