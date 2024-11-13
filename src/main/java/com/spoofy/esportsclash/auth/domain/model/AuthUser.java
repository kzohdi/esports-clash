package com.spoofy.esportsclash.auth.domain.model;

import com.spoofy.esportsclash.core.domain.model.BaseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class AuthUser extends BaseModel<AuthUser> {
    private String emailAddress;

    public AuthUser(String id, String emailAddress) {
        super(id);
        this.emailAddress = emailAddress;
    }

    @Override
    public AuthUser deepClone() {
        return new AuthUser(id, emailAddress);
    }
}
