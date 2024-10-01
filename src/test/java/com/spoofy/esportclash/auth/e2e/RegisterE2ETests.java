package com.spoofy.esportclash.auth.e2e;

import com.spoofy.esportclash.IntegrationTests;
import com.spoofy.esportclash.auth.domain.model.User;
import com.spoofy.esportclash.auth.infrastructure.spring.RegisterDTO;
import com.spoofy.esportclash.core.domain.viewmodel.IdResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterE2ETests extends IntegrationTests {

    @BeforeEach
    void setup() {
        userRepository.clear();
    }

    @Test
    void shouldRegisterUser() throws Exception {
        var dto = new RegisterDTO("contact@spoofy.com", "azerty");

        var result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var idResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                IdResponse.class
        );

        var user = userRepository.findById(idResponse.getId()).get();

        assertEquals(dto.getEmailAddress(), user.getEmailAddress());
    }

    @Test
    void whenEmailAddressIsInUse_shouldFail() throws Exception {
        var existingUser = new User(
                "123",
                "contact@spoofy.com",
                "password"
        );
        userRepository.save(existingUser);

        var dto = new RegisterDTO(existingUser.getEmailAddress(), "azerty");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
