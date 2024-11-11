package com.spoofy.esportsclash.player.infrastructure.spring.controller;

import an.awesome.pipelinr.Pipeline;
import com.spoofy.esportsclash.player.application.usecases.CreatePlayerCommand;
import com.spoofy.esportsclash.player.application.usecases.DeletePlayerCommand;
import com.spoofy.esportsclash.player.application.usecases.GetPlayerByIdCommand;
import com.spoofy.esportsclash.player.application.usecases.RenamePlayerCommand;
import com.spoofy.esportsclash.core.domain.viewmodel.IdResponse;
import com.spoofy.esportsclash.player.domain.viewmodel.PlayerViewModel;
import com.spoofy.esportsclash.player.infrastructure.spring.dto.CreatePlayerDTO;
import com.spoofy.esportsclash.player.infrastructure.spring.dto.RenamePlayerDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/players")
@Transactional
public class PlayerController {

    private final Pipeline pipeline;

    @PostMapping
    public ResponseEntity<IdResponse> createPlayer(@RequestBody CreatePlayerDTO createPlayerDTO) {
        var result = pipeline.send(new CreatePlayerCommand(createPlayerDTO.getName()));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<Void> renamePlayer(@PathVariable String id, @RequestBody RenamePlayerDTO renamePlayerDTO) {
        pipeline.send(new RenamePlayerCommand(id, renamePlayerDTO.getName()));
        return new ResponseEntity<>(HttpStatus.OK);
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
}
