package com.spoofy.esportsclash.auth.e2e;

import com.spoofy.esportsclash.IntegrationTestTemplate;
import com.spoofy.esportsclash.auth.application.ports.UserRepository;
import com.spoofy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import com.spoofy.esportsclash.auth.domain.models.User;
import com.spoofy.esportsclash.auth.domain.viewmodels.LoggedInUserViewModel;
import com.spoofy.esportsclash.auth.infrastructure.spring.dtos.LoginDTO;
import com.spoofy.esportsclash.auth.infrastructure.spring.dtos.RegisterDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LoginE2ETests extends IntegrationTestTemplate {

    @Autowired
    private PasswordHasher passwordHasher;

    @Test
    void shouldLogin() throws Exception {
        // Given
        var user = new User(
                "123",
                "contact@spoofy.com",
                passwordHasher.hash("azerty")
        );
        userRepository.save(user);

        var dto = new LoginDTO(user.getEmailAddress(), "azerty");

        // When
        var result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                LoggedInUserViewModel.class
        );

        // Then
        assertThat(response.id()).isEqualTo(user.getId());
        assertThat(response.emailAddress()).isEqualTo(user.getEmailAddress());
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
