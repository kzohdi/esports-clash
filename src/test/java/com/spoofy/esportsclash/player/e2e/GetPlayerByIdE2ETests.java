package com.spoofy.esportsclash.player.e2e;

import com.spoofy.esportsclash.IntegrationTestBase;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.model.Player;
import com.spoofy.esportsclash.player.domain.viewmodel.PlayerViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetPlayerByIdE2ETests extends IntegrationTestBase {

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
        var result = mockMvc.perform(get(String.format("/players/%s", player.getId()))
                        .header(AUTHORIZATION_HEADER, createJwt()))
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
        mockMvc.perform(get(String.format("/players/%s", garbageId))
                        .header(AUTHORIZATION_HEADER, createJwt()))
                .andExpect(status().isNotFound());
    }
}
