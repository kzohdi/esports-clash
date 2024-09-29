package com.spoofy.esportclash.core.domain.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity, String id) {
        super(String.format(
                "The entity %s with the id %s was not found",
                entity,
                id
        ));
    }
}
