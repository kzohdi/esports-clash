package com.spoofy.esportsclash.auth.application.port;

import com.spoofy.esportsclash.auth.domain.model.User;
import com.spoofy.esportsclash.core.application.port.BaseRepository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
    boolean isEmailAddressAlreadyInUse(String email);

    Optional<User> findByEmailAddress(String email);
}
