package com.spoofy.esportsclash.auth.application.ports;

import com.spoofy.esportsclash.auth.domain.model.AuthUser;

import java.util.Optional;

public interface AuthContext {
    boolean isAuthenticated();

    Optional<AuthUser> getUser();
}
