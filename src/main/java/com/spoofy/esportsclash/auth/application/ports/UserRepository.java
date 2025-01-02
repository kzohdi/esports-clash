package com.spoofy.esportsclash.auth.application.ports;

import com.spoofy.esportsclash.auth.domain.models.User;
import com.spoofy.esportsclash.core.infrastructure.persistence.BaseRepository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {

    boolean isEmailAddressAlreadyInUse(String emailAddress);

    Optional<User> findByEmailAddress(String emailAddress);

}
