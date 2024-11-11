package com.spoofy.esportsclash.auth.infrastructure.persistence.ram;

import com.spoofy.esportsclash.auth.application.port.UserRepository;
import com.spoofy.esportsclash.auth.domain.model.User;
import com.spoofy.esportsclash.core.infrastructure.persistence.ram.InMemoryBaseRepository;
import org.springframework.stereotype.Repository;

// TODO Remove annotation when JPA is implemented
@Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {
    @Override
    public boolean isEmailAddressAlreadyInUse(String email) {
        return models.values()
                .stream()
                .anyMatch(user -> user.getEmailAddress().equals(email));
    }
}
