package com.spoofy.esportsclash.auth.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.core.infrastructure.persistence.jpa.SQLBaseEntityDataAccessor;

import java.util.Optional;

public interface SQLUserDataAccessor extends SQLBaseEntityDataAccessor<SQLUser> {
    boolean existsByEmailAddress(String emailAddress);

    Optional<SQLUser> findByEmailAddress(String emailAddress);
}
