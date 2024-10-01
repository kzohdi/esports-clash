package com.spoofy.esportclash.auth.services;

import com.spoofy.esportclash.auth.application.services.jwtservice.ConcreteJwtService;
import com.spoofy.esportclash.auth.domain.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtServiceTests {

    @Test
    void shouldTokenizeTheUser() {
        var jwtService = new ConcreteJwtService(
                "super_secret_key_please_do_not_share",
                60
        );

        var user = new User(
                "123",
                "contact@spoofy.com",
                "azerty"
        );

        var token = jwtService.tokenize(user);
        var authUser = jwtService.parse(token);

        assertEquals(user.getId(), authUser.getId());
        assertEquals(user.getEmailAddress(), authUser.getEmailAddress());
    }
}
