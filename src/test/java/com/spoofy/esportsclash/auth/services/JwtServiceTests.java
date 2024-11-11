package com.spoofy.esportsclash.auth.services;

import com.spoofy.esportsclash.auth.application.services.jwtservice.JwtService;
import com.spoofy.esportsclash.auth.application.services.jwtservice.JwtServiceImpl;
import com.spoofy.esportsclash.auth.domain.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtServiceTests {

    private final JwtService jwtService = new JwtServiceImpl(60);

    @Test
    void shouldTokenize() {
        // Given
        var user = new User("123", "contact@spoofy.com", "password");

        // When
        var token = jwtService.tokenize(user);

        var authUser = jwtService.parse(token);

        // Then
        assertEquals(user.getId(), authUser.getId());
        assertEquals(user.getEmailAddress(), authUser.getEmailAddress());
    }
}
