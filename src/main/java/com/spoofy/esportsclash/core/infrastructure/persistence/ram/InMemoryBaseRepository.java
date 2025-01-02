package com.spoofy.esportsclash.core.infrastructure.persistence.ram;

import com.spoofy.esportsclash.core.domain.models.BaseModel;
import com.spoofy.esportsclash.core.infrastructure.persistence.BaseRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class InMemoryBaseRepository<T extends BaseModel<T>> implements BaseRepository<T> {

    protected final Map<String, T> models = new HashMap<>();

    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable(models.get(id)).map(BaseModel::deepClone);
    }

    @Override
    public void save(T model) {
        models.put(model.getId(), model);
    }

    @Override
    public void delete(T model) {
        models.remove(model.getId());
    }

    @Override
    public void clear() {
        models.clear();
    }
}
