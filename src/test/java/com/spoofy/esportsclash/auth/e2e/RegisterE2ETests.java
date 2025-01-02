package com.spoofy.esportsclash.auth.e2e;

import com.spoofy.esportsclash.IntegrationTestTemplate;
import com.spoofy.esportsclash.auth.application.ports.UserRepository;
import com.spoofy.esportsclash.auth.domain.models.User;
import com.spoofy.esportsclash.auth.infrastructure.spring.dtos.RegisterDTO;
import com.spoofy.esportsclash.core.domain.viewmodels.IdResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RegisterE2ETests extends IntegrationTestTemplate {

    @BeforeEach
    void setup() {
        userRepository.clear();
    }

    @Test
    void shouldRegister() throws Exception {
        // Given
        var dto = new RegisterDTO("contact@spoofy.com", "azerty");

        // When
        var result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                IdResponse.class
        );

        // Then
        var actual = userRepository.findById(response.id()).get();

        assertThat(actual.getEmailAddress()).isEqualTo(dto.emailAddress());
    }

    @Test
    void whenEmailAlreadyInUse_shouldFail() throws Exception {
        // Given
        var existingUser = new User("123", "contact@spoofy.com", "azerty");
        userRepository.save(existingUser);

        var dto = new RegisterDTO(existingUser.getEmailAddress(), "123456");

        // When
        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
