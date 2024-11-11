package com.spoofy.esportsclash.auth.infrastructure.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RegisterDTO {
    private String emailAddress;
    private String password;
}
