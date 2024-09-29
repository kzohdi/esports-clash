package com.spoofy.esportclash.auth.infrastructure.persistence.ram;

import com.spoofy.esportclash.auth.application.ports.UserRepository;
import com.spoofy.esportclash.auth.domain.model.User;
import com.spoofy.esportclash.core.infrastructure.persistence.ram.InMemoryBaseRepository;

import java.util.Optional;

public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {

    @Override
    public boolean isEmailAddressAvailable(String emailAddress) {
        return entities
                .values()
                .stream()
                .noneMatch(e -> e.getEmailAddress().equalsIgnoreCase(emailAddress));
    }

    @Override
    public Optional<User> findByEmailAddress(String emailAddress) {
        return entities
                .values()
                .stream()
                .filter(user -> user.getEmailAddress().equalsIgnoreCase(emailAddress))
                .findFirst();
    }
}
