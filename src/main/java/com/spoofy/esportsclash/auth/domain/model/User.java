package com.spoofy.esportsclash.auth.domain.model;

import com.spoofy.esportsclash.core.domain.model.BaseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class User extends BaseModel<User> {
    private String emailAddress;
    private String passwordHash;

    public User(String id, String emailAddress, String passwordHash) {
        super(id);
        this.emailAddress = emailAddress;
        this.passwordHash = passwordHash;
    }

    @Override
    public User deepClone() {
        return new User(id, emailAddress, passwordHash);
    }
}
