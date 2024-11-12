package com.spoofy.esportsclash.core.application.port;

import com.spoofy.esportsclash.core.domain.model.BaseModel;

import java.util.Optional;

public interface BaseRepository<M extends BaseModel> {
    void save(M entity);

    Optional<M> findById(String id);

    boolean existsById(String id);

    void deleteById(String id);

    void clear();
}
