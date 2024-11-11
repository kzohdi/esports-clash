package com.spoofy.esportsclash.auth.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.auth.application.port.UserRepository;
import com.spoofy.esportsclash.auth.domain.model.User;
import com.spoofy.esportsclash.core.infrastructure.persistence.jpa.SQLBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SQLUserRepository extends SQLBaseRepository<User, SQLUser> implements UserRepository {

    private final SQLUserMapper mapper;

    private final SQLUserDataAccessor dataAccessor;

    public SQLUserRepository(SQLUserMapper mapper, SQLUserDataAccessor dataAccessor) {
        super(mapper, dataAccessor);
        this.mapper = mapper;
        this.dataAccessor = dataAccessor;
    }

    @Override
    public boolean isEmailAddressAlreadyInUse(String email) {
        return dataAccessor.existsByEmailAddress(email);
    }

    @Override
    public Optional<User> findByEmailAddress(String email) {
        return dataAccessor.findByEmailAddress(email)
                .map(mapper::toModel);
    }
}
