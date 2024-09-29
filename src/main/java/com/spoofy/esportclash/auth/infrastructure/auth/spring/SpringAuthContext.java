package com.spoofy.esportclash.auth.infrastructure.auth.spring;

import com.spoofy.esportclash.auth.application.ports.AuthContext;
import com.spoofy.esportclash.auth.domain.model.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringAuthContext implements AuthContext {
    @Override
    public boolean isAuthenticated() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .isAuthenticated();
    }

    @Override
    public Optional<AuthUser> getUser() {
        return Optional.ofNullable(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
        ).map(auth -> {
            if (auth.getPrincipal() instanceof AuthUser authUser) {
                return authUser;
            }

            return null;
        });
    }
}
