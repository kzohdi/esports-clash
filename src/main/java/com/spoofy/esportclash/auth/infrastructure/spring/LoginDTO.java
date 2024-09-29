package com.spoofy.esportclash.auth.infrastructure.spring;

public class LoginDTO {
    private final String emailAddress;
    private final String password;

    public LoginDTO(String emailAddress, String password) {
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
