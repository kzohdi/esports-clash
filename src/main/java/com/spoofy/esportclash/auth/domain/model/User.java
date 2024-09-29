package com.spoofy.esportclash.auth.domain.model;

import com.spoofy.esportclash.core.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(name = "email_address")
    private final String emailAddress;

    @Column(name = "password_hash")
    private final String passwordHash;

    public User(String id, String emailAddress, String passwordHash) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.passwordHash = passwordHash;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
