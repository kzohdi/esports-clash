package com.spoofy.esportsclash.auth.e2e;

import com.spoofy.esportsclash.IntegrationTestBase;
import com.spoofy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import com.spoofy.esportsclash.auth.domain.model.User;
import com.spoofy.esportsclash.auth.infrastructure.spring.dto.RegisterDTO;
import com.spoofy.esportsclash.core.domain.viewmodel.IdResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class RegisterE2ETests extends IntegrationTestBase {

    @Autowired
    private PasswordHasher passwordHasher;

    @BeforeEach
    void setup() {
        userRepository.clear();
    }

    @Test
    void shouldRegisterUser() throws Exception {
        // Given
        var registerDTO = new RegisterDTO("contact@spoofy.com", "password");

        // When
        var result = mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        var idResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                IdResponse.class);

        // Then
        var actualUser = userRepository.findById(idResponse.getId()).get();
        assertEquals(registerDTO.getEmailAddress(), actualUser.getEmailAddress());
        assertTrue(passwordHasher.match(registerDTO.getPassword(), actualUser.getPasswordHash()));
    }

    @Test
    void whenUserAlreadyExists_shouldThrow() throws Exception {
        // Given
        var existingUser = new User("123", "contact@spoofy.com", "password");
        userRepository.save(existingUser);

        var registerDTO = new RegisterDTO(existingUser.getEmailAddress(), existingUser.getPasswordHash());

        // When
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isBadRequest());
    }
}
