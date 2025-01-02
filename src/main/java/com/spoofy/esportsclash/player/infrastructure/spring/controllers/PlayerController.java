package com.spoofy.esportsclash.player.infrastructure.spring.controllers;

import an.awesome.pipelinr.Pipeline;
import com.spoofy.esportsclash.player.application.usecases.DeletePlayerCommand;
import com.spoofy.esportsclash.player.application.usecases.GetPlayerByIdCommand;
import com.spoofy.esportsclash.player.application.usecases.RenamePlayerCommand;
import com.spoofy.esportsclash.player.domain.viewmodels.PlayerViewModel;
import com.spoofy.esportsclash.player.infrastructure.spring.dtos.CreatePlayerDTO;
import com.spoofy.esportsclash.core.domain.viewmodels.IdResponse;
import com.spoofy.esportsclash.player.application.usecases.CreatePlayerCommand;
import com.spoofy.esportsclash.player.infrastructure.spring.dtos.RenamePlayerDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players")
@Transactional
public class PlayerController {

    private final Pipeline pipeline;


    public PlayerController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @PostMapping
    public ResponseEntity<IdResponse> createPlayer(@RequestBody CreatePlayerDTO dto) {
        var result = pipeline.send(new CreatePlayerCommand(dto.name()));

        return new ResponseEntity<>(new IdResponse(result.id()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable String id) {
        pipeline.send(new DeletePlayerCommand(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerViewModel> getPlayerById(@PathVariable String id) {
        var result = pipeline.send(new GetPlayerByIdCommand(id));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<Void> renamePlayer(@PathVariable String id, @RequestBody RenamePlayerDTO dto) {
        pipeline.send(new RenamePlayerCommand(id, dto.newName()));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
