package com.spoofy.esportsclash.player.e2e;

import com.spoofy.esportsclash.IntegrationTestTemplate;
import com.spoofy.esportsclash.TestcontainersConfiguration;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.models.Player;
import com.spoofy.esportsclash.player.infrastructure.spring.dtos.RenamePlayerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
class RenamePlayerE2ETests extends IntegrationTestTemplate {

    @Autowired
    private PlayerRepository playerRepository;

    @BeforeEach
    void setup() {
        playerRepository.clear();
    }

    @Test
    void shouldCreatePlayer() throws Exception {
        // Given
        playerRepository.save(new Player("123", "Old Name"));

        var dto = new RenamePlayerDTO("New Name");

        // When
        mockMvc.perform(MockMvcRequestBuilders.patch("/players/123/name")
                        .header("Authorization", createJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Then
        var actualPlayer = playerRepository.findById("123").get();

        assertThat(actualPlayer.getName()).isEqualTo(dto.newName());
    }

    @Test
    void whenPlayerNotFound_shouldFail() throws Exception {
        // Given
        var dto = new RenamePlayerDTO("New Name");

        // When
        // Then
        mockMvc.perform(MockMvcRequestBuilders.patch("/players/123/name")
                        .header("Authorization", createJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
