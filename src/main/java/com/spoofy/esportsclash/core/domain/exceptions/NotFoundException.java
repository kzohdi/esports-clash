package com.spoofy.esportsclash.core.domain.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String model, String id) {
        super(String.format("The entity %s with the id %s was not found", model, id));
    }

    public NotFoundException(String model) {
        super(String.format("The entity %s was not found", model));
    }
}
