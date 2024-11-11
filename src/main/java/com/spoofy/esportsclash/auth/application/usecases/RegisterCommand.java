package com.spoofy.esportsclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.core.domain.viewmodel.IdResponse;

public record RegisterCommand(String emailAddress, String password) implements Command<IdResponse> {
}
