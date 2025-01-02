package com.spoofy.esportsclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.auth.domain.viewmodels.LoggedInUserViewModel;

public record LoginCommand(String emailAddress, String password) implements Command<LoggedInUserViewModel> {
}
