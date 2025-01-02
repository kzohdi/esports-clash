package com.spoofy.esportsclash.auth.services;

import com.spoofy.esportsclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PasswordHasherTests {

    @Test
    void shouldHashPassword() {
        // Given
        var passwordHasher = new BcryptPasswordHasher();
        var clearPassword = "azerty";

        // When
        var result = passwordHasher.hash(clearPassword);

        // Then
        assertThat(passwordHasher.matches(clearPassword, result)).isTrue();
    }
}
