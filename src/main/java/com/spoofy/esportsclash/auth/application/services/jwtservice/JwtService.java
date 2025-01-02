package com.spoofy.esportsclash.auth.application.services.jwtservice;

import com.spoofy.esportsclash.auth.domain.models.AuthUser;
import com.spoofy.esportsclash.auth.domain.models.User;

public interface JwtService {

    String tokenize(User user);

    AuthUser decode(String token);
}
