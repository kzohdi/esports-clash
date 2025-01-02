package com.spoofy.esportsclash.auth.application.exceptions;

import com.spoofy.esportsclash.core.domain.exceptions.BadRequestException;

public class EmailAlreadyInUseException extends BadRequestException {
    public EmailAlreadyInUseException() {
        super("The email address is already in use");
    }
}
