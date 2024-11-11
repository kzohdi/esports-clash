package com.spoofy.esportsclash.auth.application.services.jwtservice;

import com.spoofy.esportsclash.auth.domain.model.AuthUser;
import com.spoofy.esportsclash.auth.domain.model.User;

public interface JwtService {
    String tokenize(User user);

    AuthUser parse(String token);
}
