package com.spoofy.esportsclash.auth.infrastructure.spring.controllers;

import an.awesome.pipelinr.Pipeline;
import com.spoofy.esportsclash.auth.application.usecases.LoginCommand;
import com.spoofy.esportsclash.auth.application.usecases.RegisterCommand;
import com.spoofy.esportsclash.auth.domain.viewmodels.LoggedInUserViewModel;
import com.spoofy.esportsclash.auth.infrastructure.spring.dtos.LoginDTO;
import com.spoofy.esportsclash.auth.infrastructure.spring.dtos.RegisterDTO;
import com.spoofy.esportsclash.core.domain.viewmodels.IdResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Transactional
public class AuthController {

    private final Pipeline pipeline;

    public AuthController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @PostMapping("/register")
    public ResponseEntity<IdResponse> register(@RequestBody RegisterDTO dto) {
        var result = pipeline.send(new RegisterCommand(dto.emailAddress(), dto.password()));

        return new ResponseEntity<>(new IdResponse(result.id()), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoggedInUserViewModel> login(@RequestBody LoginDTO dto) {
        var result = pipeline.send(new LoginCommand(dto.emailAddress(), dto.password()));

        return new ResponseEntity<>(
                new LoggedInUserViewModel(
                        result.id(),
                        result.emailAddress(),
                        result.token()),
                HttpStatus.OK);
    }
}
