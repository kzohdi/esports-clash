package com.spoofy.esportsclash.core.infrastructure.persistence.ram;

import com.spoofy.esportsclash.core.application.port.BaseRepository;
import com.spoofy.esportsclash.core.domain.model.BaseModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class InMemoryBaseRepository<M extends BaseModel<M>> implements BaseRepository<M> {
    protected final Map<String, M> models = new HashMap<>();

    @Override
    public void save(M model) {
        models.put(model.getId(), model.deepClone());
    }

    @Override
    public Optional<M> findById(String id) {
        return Optional.ofNullable(models.get(id))
                .map(BaseModel::deepClone);
    }

    @Override
    public boolean existsById(String id) {
        return models.containsKey(id);
    }

    @Override
    public void deleteById(String id) {
        models.remove(id);
    }

    @Override
    public void clear() {
        models.clear();
    }
}
