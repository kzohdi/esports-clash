package com.spoofy.esportclash.auth.application.ports;

import com.spoofy.esportclash.auth.domain.model.AuthUser;

import java.util.Optional;

public interface AuthContext {
    boolean isAuthenticated();

    Optional<AuthUser> getUser();
}
