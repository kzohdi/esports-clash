package com.spoofy.esportsclash.core.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SQLBaseEntityDataAccessor<E extends SQLEntity> extends JpaRepository<E, String> {
}
