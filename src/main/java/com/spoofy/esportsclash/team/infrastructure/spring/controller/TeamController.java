package com.spoofy.esportsclash.team.infrastructure.spring.controller;

import an.awesome.pipelinr.Pipeline;
import com.spoofy.esportsclash.core.domain.viewmodel.IdResponse;
import com.spoofy.esportsclash.team.application.usecases.*;
import com.spoofy.esportsclash.team.domain.viewmodel.TeamViewModel;
import com.spoofy.esportsclash.team.infrastructure.spring.dto.AddPlayerToTeamDTO;
import com.spoofy.esportsclash.team.infrastructure.spring.dto.CreateTeamDTO;
import com.spoofy.esportsclash.team.infrastructure.spring.dto.RemovePlayerFromTeamDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
@Transactional
public class TeamController {

    private final Pipeline pipeline;

    @PostMapping
    public ResponseEntity<IdResponse> createTeam(@RequestBody CreateTeamDTO dto) {
        var result = pipeline.send(new CreateTeamCommand(dto.getName()));

        return new ResponseEntity<>(new IdResponse(result.getId()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable String id) {
        pipeline.send(new DeleteTeamCommand(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/add-player-to-team")
    public ResponseEntity<Void> addPlayerToTeam(@RequestBody AddPlayerToTeamDTO dto) {
        pipeline.send(new AddPlayerToTeamCommand(
                dto.getPlayerId(),
                dto.getTeamId(),
                dto.getRole()));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/remove-player-from-team")
    public ResponseEntity<Void> removePlayerFromTeam(@RequestBody RemovePlayerFromTeamDTO dto) {
        pipeline.send(new RemovePlayerFromTeamCommand(
                dto.getPlayerId(),
                dto.getTeamId()));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamViewModel> getTeamById(@PathVariable String id) {
        var result = pipeline.send(new GetTeamByIdCommand(id));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
