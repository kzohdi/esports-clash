package com.spoofy.esportsclash.core.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.core.domain.models.BaseModel;
import com.spoofy.esportsclash.core.infrastructure.persistence.BaseRepository;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public abstract class SQLBaseRepository<T extends BaseModel<T>> implements BaseRepository<T> {

    protected final EntityManager entityManager;

    protected SQLBaseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public abstract Class<T> getEntityClass();

    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable(entityManager.find(getEntityClass(), id));
    }

    @Override
    public void save(T model) {
        entityManager.persist(model);
    }

    @Override
    public void delete(T model) {
        entityManager.remove(model);
    }

    @Override
    public void clear() {
        entityManager.createQuery("DELETE FROM " + getEntityClass().getSimpleName()).executeUpdate();
    }
}
