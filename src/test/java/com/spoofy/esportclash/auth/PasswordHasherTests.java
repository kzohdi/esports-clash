package com.spoofy.esportclash.auth;

import com.spoofy.esportclash.auth.application.services.passwordhasher.BCryptPasswordHasher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordHasherTests {

    public BCryptPasswordHasher createHasher() {
        return new BCryptPasswordHasher();
    }

    @Test
    void shouldHashPassword() {
        var clearPassword = "azerty";

        var hasher = createHasher();
        var hashedPassword = hasher.hash(clearPassword);

        assertTrue(hasher.match(clearPassword, hashedPassword));
    }
}
