package com.spoofy.esportsclash.auth.application.services.passwordhasher;

public interface PasswordHasher {
    String hash(String clearPassword);

    boolean matches(String clearPassword, String passwordHash);
}
