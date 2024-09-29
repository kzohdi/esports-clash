package com.spoofy.esportclash.auth.domain.model;

public class AuthUser {
    private final String id;
    private final String emailAddress;

    public AuthUser(String id, String emailAddress) {
        this.id = id;
        this.emailAddress = emailAddress;
    }

    public String getId() {
        return id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
