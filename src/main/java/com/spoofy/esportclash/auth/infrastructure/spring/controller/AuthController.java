package com.spoofy.esportclash.auth.infrastructure.spring.controller;

import an.awesome.pipelinr.Pipeline;
import com.spoofy.esportclash.auth.application.usecases.LoginCommand;
import com.spoofy.esportclash.auth.application.usecases.RegisterCommand;
import com.spoofy.esportclash.auth.domain.viewmodel.LoggedInUserViewModel;
import com.spoofy.esportclash.auth.infrastructure.spring.dto.LoginDTO;
import com.spoofy.esportclash.auth.infrastructure.spring.dto.RegisterDTO;
import com.spoofy.esportclash.core.domain.viewmodel.IdResponse;
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
    public ResponseEntity<IdResponse> register(@RequestBody RegisterDTO registerDTO) {
        var result = pipeline.send(
                new RegisterCommand(
                        registerDTO.getEmailAddress(),
                        registerDTO.getPassword()));

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoggedInUserViewModel> login(@RequestBody LoginDTO loginDTO) {
        var result = pipeline.send(
                new LoginCommand(
                        loginDTO.getEmailAddress(),
                        loginDTO.getPassword()));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
