package com.spoofy.esportsclash.auth.application.exceptions;

import com.spoofy.esportsclash.core.domain.exceptions.BadRequestException;

public class EmailAddressAlreadyInUseException extends BadRequestException {
    public EmailAddressAlreadyInUseException() {
        super("Email address is already in use");
    }
}
