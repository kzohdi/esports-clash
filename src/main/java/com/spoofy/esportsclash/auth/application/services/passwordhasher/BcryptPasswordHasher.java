package com.spoofy.esportsclash.auth.application.services.passwordhasher;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptPasswordHasher implements PasswordHasher {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String hash(String clearPassword) {
        return encoder.encode(clearPassword);
    }

    @Override
    public boolean matches(String clearPassword, String passwordHash) {
        return encoder.matches(clearPassword, passwordHash);
    }
}
