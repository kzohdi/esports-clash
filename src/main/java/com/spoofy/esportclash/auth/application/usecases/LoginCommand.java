package com.spoofy.esportclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportclash.auth.domain.viewmodel.LoggedInUserViewModel;

public class LoginCommand implements Command<LoggedInUserViewModel> {
    private final String emailAddress;
    private final String password;

    public LoginCommand(String emailAddress, String password) {
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
