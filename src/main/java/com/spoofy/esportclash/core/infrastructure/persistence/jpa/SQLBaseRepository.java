package com.spoofy.esportclash.core.infrastructure.persistence.jpa;

import com.spoofy.esportclash.core.domain.model.BaseEntity;
import com.spoofy.esportclash.core.infrastructure.persistence.BaseRepository;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public abstract class SQLBaseRepository<T extends BaseEntity<T>> implements BaseRepository<T> {
    protected final EntityManager entityManager;

    protected SQLBaseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public abstract Class<T> getEntityClass();

    @Override
    public void save(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable(
                entityManager.find(getEntityClass(), id)
        );
    }

    @Override
    public void clear() {
        entityManager.createQuery("DELETE FROM " + getEntityClass().getSimpleName()).executeUpdate();
    }
}
