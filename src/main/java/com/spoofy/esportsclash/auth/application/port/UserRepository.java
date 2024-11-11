package com.spoofy.esportsclash.auth.application.port;

import com.spoofy.esportsclash.auth.domain.model.User;
import com.spoofy.esportsclash.core.infrastructure.persistence.BaseRepository;

public interface UserRepository extends BaseRepository<User> {
    boolean isEmailAddressAlreadyInUse(String email);
}
