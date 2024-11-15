package com.spoofy.esportsclash.core.domain.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity, String key) {
        super(String.format("%s with the key %s not found", entity, key));
    }
    public NotFoundException(String entity) {
        super(String.format("%s was not found", entity));
    }
}
