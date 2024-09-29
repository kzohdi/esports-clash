package com.spoofy.esportclash.auth;

import com.spoofy.esportclash.IntegrationTests;
import com.spoofy.esportclash.auth.application.services.passwordhasher.PasswordHasher;
import com.spoofy.esportclash.auth.domain.model.User;
import com.spoofy.esportclash.auth.domain.viewmodel.LoggedInUserViewModel;
import com.spoofy.esportclash.auth.infrastructure.spring.LoginDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginE2ETests extends IntegrationTests {

    @Autowired
    private PasswordHasher passwordHasher;

    @BeforeEach
    void setup() {
        userRepository.clear();
        var user = new User(
                "123",
                "contact@spoofy.com",
                passwordHasher.hash("azerty")
        );
        userRepository.save(user);
    }

    @Test
    void shouldLogTheUserIn() throws Exception {
        var dto = new LoginDTO("contact@spoofy.com", "azerty");

        var result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var loggedInUser = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                LoggedInUserViewModel.class
        );

        assertEquals("123", loggedInUser.getId());
        assertEquals(dto.getEmailAddress(), loggedInUser.getEmailAddress());
    }

    @Test
    void whenEmailAddressIsInvalid_shouldFail() throws Exception {
        var dto = new LoginDTO("johndoe@spoofy.com", "azerty");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenPasswordIsInvalid_shouldFail() throws Exception {
        var dto = new LoginDTO("contact@spoofy.com", "password");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
