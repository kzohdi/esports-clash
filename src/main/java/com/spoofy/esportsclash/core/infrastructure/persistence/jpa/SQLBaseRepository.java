package com.spoofy.esportsclash.core.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.core.domain.model.BaseModel;
import com.spoofy.esportsclash.core.infrastructure.persistence.BaseRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class SQLBaseRepository<M extends BaseModel, E extends SQLEntity> implements BaseRepository<M> {

    private final SQLBaseEntityMapper<M, E> mapper;
    private final SQLBaseEntityDataAccessor<E> dataAccessor;

    @Override
    public void save(M model) {
        E sqlEntity = mapper.toEntity(model);

        dataAccessor.save(sqlEntity);
    }

    @Override
    public Optional<M> findById(String id) {
        return dataAccessor.findById(id)
                .map(mapper::toModel);
    }

    @Override
    public boolean existsById(String id) {
        return dataAccessor.existsById(id);
    }

    @Override
    public void deleteById(String id) {
        dataAccessor.deleteById(id);
    }

    @Override
    public void clear() {
        dataAccessor.deleteAll();
    }
}
