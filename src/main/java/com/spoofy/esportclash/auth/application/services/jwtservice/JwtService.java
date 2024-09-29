package com.spoofy.esportclash.auth.application.services.jwtservice;

import com.spoofy.esportclash.auth.domain.model.AuthUser;
import com.spoofy.esportclash.auth.domain.model.User;

public interface JwtService {
    String tokenize(User user);

    AuthUser parse(String token);
}
