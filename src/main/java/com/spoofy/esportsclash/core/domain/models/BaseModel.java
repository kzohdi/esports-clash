package com.spoofy.esportsclash.core.domain.models;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseModel<T> {
    @Id
    protected String id;

    protected BaseModel() {
    }

    protected BaseModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public abstract T deepClone();
}
