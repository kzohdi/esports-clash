package com.spoofy.esportsclash.auth.infrastructure.spring.config;

import com.spoofy.esportsclash.auth.application.port.AuthContext;
import com.spoofy.esportsclash.auth.domain.model.AuthUser;
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
        return Optional.ofNullable(SecurityContextHolder
                .getContext()
                .getAuthentication()
        ).map(authentication -> {
            var principal = authentication.getPrincipal();
            if (principal instanceof AuthUser authUser) {
                return authUser;
            }
            return null;
        });
    }
}
