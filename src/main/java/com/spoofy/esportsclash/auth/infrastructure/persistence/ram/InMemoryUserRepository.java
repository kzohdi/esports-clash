package com.spoofy.esportsclash.auth.infrastructure.persistence.ram;

import com.spoofy.esportsclash.auth.application.port.UserRepository;
import com.spoofy.esportsclash.auth.domain.model.User;
import com.spoofy.esportsclash.core.infrastructure.persistence.ram.InMemoryBaseRepository;

import java.util.Optional;


public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {
    @Override
    public boolean isEmailAddressAlreadyInUse(String email) {
        return models.values()
                .stream()
                .anyMatch(user -> user.getEmailAddress().equals(email));
    }

    @Override
    public Optional<User> findByEmailAddress(String email) {
        return models.values()
                .stream()
                .filter(user -> user.getEmailAddress().equals(email))
                .findFirst();
    }
}
