package com.spoofy.esportsclash.auth.e2e;

import com.spoofy.esportsclash.IntegrationTestBase;
import com.spoofy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import com.spoofy.esportsclash.auth.domain.model.User;
import com.spoofy.esportsclash.auth.domain.viewmodel.LoggedInUserViewModel;
import com.spoofy.esportsclash.auth.infrastructure.spring.dto.LoginDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class LoginE2ETests extends IntegrationTestBase {

    @Autowired
    private PasswordHasher passwordHasher;

    private User user;

    private static final String CLEAR_PASSWORD = "password";

    @BeforeEach
    public void setup() {
        userRepository.clear();

        user = new User(
                "123",
                "contact@spoofy.com",
                passwordHasher.hash(CLEAR_PASSWORD)
        );
        userRepository.save(user);
    }

    @Test
    void shouldLogTheUserIn() throws Exception {
        // Given
        var loginDTO = new LoginDTO(user.getEmailAddress(), CLEAR_PASSWORD);

        // When
        var result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andReturn();

        var loggedInUser = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                LoggedInUserViewModel.class);

        // Then
        assertEquals(user.getId(), loggedInUser.getId());
        assertEquals(user.getEmailAddress(), loggedInUser.getEmailAddress());
    }

    @Test
    void whenEmailIsInvalid_shouldFail() throws Exception {
        // Given
        var loginDTO = new LoginDTO("contact@spoofy.fr", CLEAR_PASSWORD);

        // When
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPasswordIsInvalid_shouldFail() throws Exception {
        // Given
        var loginDTO = new LoginDTO(user.getEmailAddress(), "azerty");

        // When
         mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isBadRequest());
    }
}
