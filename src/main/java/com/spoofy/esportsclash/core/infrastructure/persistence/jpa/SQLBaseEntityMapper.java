package com.spoofy.esportsclash.core.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.core.domain.model.BaseModel;

public interface SQLBaseEntityMapper<M extends BaseModel, E extends SQLEntity> {

    E toSQLBaseEntity(M model);

    M toBaseEntity(E entity);

}
