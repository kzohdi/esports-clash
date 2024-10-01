package com.spoofy.esportclash.player.infrastructure.spring.controller;

import an.awesome.pipelinr.Pipeline;
import com.spoofy.esportclash.core.domain.viewmodel.IdResponse;
import com.spoofy.esportclash.player.application.usecases.CreatePlayerCommand;
import com.spoofy.esportclash.player.application.usecases.DeletePlayerCommand;
import com.spoofy.esportclash.player.application.usecases.GetPlayerByIdCommand;
import com.spoofy.esportclash.player.application.usecases.RenamePlayerCommand;
import com.spoofy.esportclash.player.domain.viewmodel.PlayerViewModel;
import com.spoofy.esportclash.player.infrastructure.spring.dto.CreatePlayerDTO;
import com.spoofy.esportclash.player.infrastructure.spring.dto.RenamePlayerDTO;
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
        var result = pipeline.send(new CreatePlayerCommand(dto.getName()));

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<Void> renamePlayer(@PathVariable("id") String id, @RequestBody RenamePlayerDTO renamePlayerDTO) {
        pipeline.send(new RenamePlayerCommand(id, renamePlayerDTO.getName()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable("id") String id) {
        pipeline.send(new DeletePlayerCommand(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerViewModel> getPlayerById(@PathVariable("id") String id) {
        var player = pipeline.send(new GetPlayerByIdCommand(id));
        return new ResponseEntity<>(player, HttpStatus.OK);
    }
}
