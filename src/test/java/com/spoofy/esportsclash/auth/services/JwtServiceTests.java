package com.spoofy.esportsclash.auth.services;

import com.spoofy.esportsclash.auth.application.services.jwtservice.ConcreteJwtService;
import com.spoofy.esportsclash.auth.domain.models.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class JwtServiceTests {

    @Test
    void shouldTokenizeUser() {
        // Given
        var jwtService = new ConcreteJwtService(3600);
        var user = new User("123", "contact@spoofy.com", "azerty");

        // When
        var token = jwtService.tokenize(user);
        var authUser = jwtService.decode(token);

        // Then
        assertThat(authUser.id()).isEqualTo(user.getId());
        assertThat(authUser.emailAddress()).isEqualTo(user.getEmailAddress());
    }
}
