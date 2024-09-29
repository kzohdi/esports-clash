package com.spoofy.esportclash.player;

import com.spoofy.esportclash.IntegrationTests;
import com.spoofy.esportclash.player.application.ports.PlayerRepository;
import com.spoofy.esportclash.player.domain.model.Player;
import com.spoofy.esportclash.player.domain.viewmodel.PlayerViewModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetPlayerByIdE2ETests extends IntegrationTests {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void shouldGetPlayerById() throws Exception {
        var existingPlayer = new Player("123", "name");
        playerRepository.save(existingPlayer);

        var result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/players/" + existingPlayer.getId())
                        .header("Authorization", createJwt())
                )
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
                        .get("/players/BadID")
                        .header("Authorization", createJwt()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
