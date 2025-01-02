package com.spoofy.esportsclash.auth.infrastructure.persistence.ram;

import com.spoofy.esportsclash.auth.application.ports.UserRepository;
import com.spoofy.esportsclash.auth.domain.models.User;
import com.spoofy.esportsclash.core.infrastructure.persistence.ram.InMemoryBaseRepository;

import java.util.Optional;

public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {

    @Override
    public boolean isEmailAddressAlreadyInUse(String emailAddress) {
        return models.values()
                .stream()
                .anyMatch(user -> user.getEmailAddress().equals(emailAddress));
    }

    @Override
    public Optional<User> findByEmailAddress(String emailAddress) {
        return models.values()
                .stream()
                .filter(user -> user.getEmailAddress().equals(emailAddress))
                .findFirst();
    }
}
