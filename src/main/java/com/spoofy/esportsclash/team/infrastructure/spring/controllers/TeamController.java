package com.spoofy.esportsclash.team.infrastructure.spring.controllers;

import an.awesome.pipelinr.Pipeline;
import com.spoofy.esportsclash.core.domain.viewmodels.IdResponse;
import com.spoofy.esportsclash.team.application.usecases.*;
import com.spoofy.esportsclash.team.domain.models.Role;
import com.spoofy.esportsclash.team.domain.viewmodels.TeamViewModel;
import com.spoofy.esportsclash.team.infrastructure.spring.dtos.AddPlayerToTeamDTO;
import com.spoofy.esportsclash.team.infrastructure.spring.dtos.CreateTeamDTO;
import com.spoofy.esportsclash.team.infrastructure.spring.dtos.RemovePlayerFromTeamDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
@Transactional
public class TeamController {

    private final Pipeline pipeline;

    public TeamController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @PostMapping
    public ResponseEntity<IdResponse> createTeam(@RequestBody CreateTeamDTO dto) {
        var result = pipeline.send(new CreateTeamCommand(dto.name()));

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable String id) {
        pipeline.send(new DeleteTeamCommand(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/add-player-to-team")
    public ResponseEntity<Void> addPlayerToTeam(@RequestBody AddPlayerToTeamDTO dto) {
        pipeline.send(new AddPlayerToTeamCommand(dto.teamId(), dto.playerId(), Role.valueOf(dto.role())));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/remove-player-from-team")
    public ResponseEntity<Void> removePlayerFromTeam(@RequestBody RemovePlayerFromTeamDTO dto) {
        pipeline.send(new RemovePlayerFromTeamCommand(dto.teamId(), dto.playerId()));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamViewModel> getTeamById(@PathVariable String id) {
        var result = pipeline.send(new GetTeamByIdCommand(id));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
