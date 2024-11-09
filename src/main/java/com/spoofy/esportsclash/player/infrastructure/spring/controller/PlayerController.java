package com.spoofy.esportsclash.player.infrastructure.spring.controller;

import an.awesome.pipelinr.Pipeline;
import com.spoofy.esportsclash.player.application.usecases.CreatePlayerCommand;
import com.spoofy.esportsclash.player.domain.viewmodel.IdResponse;
import com.spoofy.esportsclash.player.infrastructure.spring.dto.CreatePlayerDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/players")
public class PlayerController {

    private final Pipeline pipeline;

    @PostMapping
    public ResponseEntity<IdResponse> createPlayer(@RequestBody CreatePlayerDTO createPlayerDTO) {
        var result = pipeline.send(new CreatePlayerCommand(createPlayerDTO.getName()));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
