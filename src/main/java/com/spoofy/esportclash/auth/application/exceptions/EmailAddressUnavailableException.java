package com.spoofy.esportclash.auth.application.exceptions;

import com.spoofy.esportclash.core.domain.exceptions.BadRequestException;

public class EmailAddressUnavailableException extends BadRequestException {
    public EmailAddressUnavailableException() {
        super("The email address is already in use");
    }
}
