package com.spoofy.esportsclash.auth.infrastructure.controller;

import an.awesome.pipelinr.Pipeline;
import com.spoofy.esportsclash.auth.application.usecases.RegisterCommand;
import com.spoofy.esportsclash.auth.infrastructure.spring.dto.RegisterDTO;
import com.spoofy.esportsclash.core.domain.viewmodel.IdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final Pipeline pipeline;

    @PostMapping("/register")
    public ResponseEntity<IdResponse> register(@RequestBody RegisterDTO registerDTO) {
        var result = pipeline.send(new RegisterCommand(registerDTO.getEmailAddress(), registerDTO.getPassword()));

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
