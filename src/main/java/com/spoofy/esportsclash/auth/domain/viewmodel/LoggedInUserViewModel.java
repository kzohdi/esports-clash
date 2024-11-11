package com.spoofy.esportsclash.auth.domain.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoggedInUserViewModel {
    private String id;
    private String emailAddress;
    private String token;
}
