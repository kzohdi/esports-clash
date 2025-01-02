package com.spoofy.esportsclash.core.infrastructure.persistence;

import com.spoofy.esportsclash.core.domain.models.BaseModel;

import java.util.Optional;

public interface BaseRepository<T extends BaseModel<T>> {
    Optional<T> findById(String id);

    void save(T model);

    void delete(T model);

    void clear();
}
