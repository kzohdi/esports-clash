package com.spoofy.esportclash.core.infrastructure.persistence;

import com.spoofy.esportclash.core.domain.model.BaseEntity;

import java.util.Optional;

public interface BaseRepository<T extends BaseEntity> {
    void save(T entity);

    void delete(T entity);

    Optional<T> findById(String id);

    void clear();
}
