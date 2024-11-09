package com.spoofy.esportsclash.player.e2e;

import com.spoofy.esportsclash.PostgreSQLTestConfiguration;
import com.spoofy.esportsclash.player.application.port.PlayerRepository;
import com.spoofy.esportsclash.player.domain.model.Player;
import com.spoofy.esportsclash.player.domain.viewmodel.PlayerViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgreSQLTestConfiguration.class)
public class GetPlayerByIdE2ETests {

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
    void shouldGetPlayerById() throws Exception {
        // Given
        var player = new Player("123", "Name");
        playerRepository.save(player);

        // When
        var result = mockMvc.perform(get(String.format("/players/%s", player.getId())))
                .andExpect(status().isOk())
                .andReturn();

        var actualPlayer = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                PlayerViewModel.class);

        // Then
        assertEquals(player.getId(), actualPlayer.getId());
        assertEquals(player.getName(), actualPlayer.getName());
    }

    @Test
    void whenPlayerNotFound_shouldThrow() throws Exception {
        // Given
        var garbageId = "123";

        // When
        mockMvc.perform(get(String.format("/players/%s", garbageId)))
                .andExpect(status().isNotFound());
    }
}
