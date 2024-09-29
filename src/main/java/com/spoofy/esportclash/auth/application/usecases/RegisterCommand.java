package com.spoofy.esportclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportclash.core.domain.viewmodel.IdResponse;

public class RegisterCommand implements Command<IdResponse> {
    private final String emailAddress;
    private final String password;

    public RegisterCommand(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }
}
