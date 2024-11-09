package com.spoofy.esportsclash.core.infrastructure.persistence;

import java.util.Optional;

public interface BaseRepository<T> {
    void save(T entity);

    Optional<T> findById(String id);

    boolean existsById(String id);

    void deleteById(String id);

    void clear();
}
