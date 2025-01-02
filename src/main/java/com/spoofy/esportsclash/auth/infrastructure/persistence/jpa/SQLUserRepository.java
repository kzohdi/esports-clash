package com.spoofy.esportsclash.auth.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.auth.application.ports.UserRepository;
import com.spoofy.esportsclash.auth.domain.models.User;
import com.spoofy.esportsclash.core.infrastructure.persistence.jpa.SQLBaseRepository;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class SQLUserRepository extends SQLBaseRepository<User> implements UserRepository {

    private final SQLUserDataAccessor dataAccessor;

    public SQLUserRepository(EntityManager entityManager, SQLUserDataAccessor dataAccessor) {
        super(entityManager);
        this.dataAccessor = dataAccessor;
    }

    @Override
    public boolean isEmailAddressAlreadyInUse(String emailAddress) {
        return dataAccessor.existsByEmailAddress(emailAddress);
    }

    @Override
    public Optional<User> findByEmailAddress(String emailAddress) {
        return dataAccessor.findByEmailAddress(emailAddress);
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}
