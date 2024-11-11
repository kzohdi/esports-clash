package com.spoofy.esportsclash.core.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.core.domain.model.BaseModel;

public interface SQLBaseEntityMapper<M extends BaseModel, E extends SQLEntity> {

    E toEntity(M model);

    M toModel(E entity);

}
