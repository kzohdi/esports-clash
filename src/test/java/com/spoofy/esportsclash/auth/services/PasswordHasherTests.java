package com.spoofy.esportsclash.auth.services;

import com.spoofy.esportsclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import com.spoofy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordHasherTests {

    private final PasswordHasher passwordHasher = new BcryptPasswordHasher();

    @Test
    void shouldHashPassword() {
        // Given
        var password = "password";

        // When
        var hashedPassword = passwordHasher.hash(password);

        // Then
        assertTrue(passwordHasher.match(password, hashedPassword));
    }
}
