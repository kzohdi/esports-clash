package com.spoofy.esportsclash.auth.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.auth.domain.models.User;

import java.util.Optional;

public interface SQLUserDataAccessor extends org.springframework.data.jpa.repository.JpaRepository<User, String> {

    boolean existsByEmailAddress(String emailAddress);

    Optional<User> findByEmailAddress(String emailAddress);
}
