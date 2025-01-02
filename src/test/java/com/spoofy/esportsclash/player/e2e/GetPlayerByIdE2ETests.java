package com.spoofy.esportsclash.player.e2e;

import com.spoofy.esportsclash.IntegrationTestTemplate;
import com.spoofy.esportsclash.TestcontainersConfiguration;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.models.Player;
import com.spoofy.esportsclash.player.domain.viewmodels.PlayerViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
class GetPlayerByIdE2ETests extends IntegrationTestTemplate {

    @Autowired
    private PlayerRepository playerRepository;

    @BeforeEach
    void setup() {
        playerRepository.clear();
    }

    @Test
    void shouldGetPlayerById() throws Exception {
        // Given
        playerRepository.save(new Player("123", "Spoofy"));

        // When
        var result = mockMvc.perform(MockMvcRequestBuilders.get("/players/123")
                        .header("Authorization", createJwtToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                PlayerViewModel.class
        );

        // Then
        assertThat(response.id()).isEqualTo("123");
        assertThat(response.name()).isEqualTo("Spoofy");
    }

    @Test
    void whenPlayerNotFound_shouldFail() throws Exception {
        // When
        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/players/123")
                        .header("Authorization", createJwtToken()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
