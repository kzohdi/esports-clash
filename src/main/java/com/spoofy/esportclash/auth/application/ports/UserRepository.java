package com.spoofy.esportclash.auth.application.ports;

import com.spoofy.esportclash.auth.domain.model.User;
import com.spoofy.esportclash.core.infrastructure.persistence.BaseRepository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
    boolean isEmailAddressAvailable(String emailAddress);

    Optional<User> findByEmailAddress(String emailAddress);
}
