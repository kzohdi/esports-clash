package com.spoofy.esportsclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.core.domain.viewmodels.IdResponse;

public record RegisterCommand(String emailAddress, String clearPassword) implements Command<IdResponse> {
}
