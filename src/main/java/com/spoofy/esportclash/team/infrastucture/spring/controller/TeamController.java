package com.spoofy.esportclash.team.infrastucture.spring.controller;

import an.awesome.pipelinr.Pipeline;
import com.spoofy.esportclash.core.domain.viewmodel.IdResponse;
import com.spoofy.esportclash.team.application.usecases.*;
import com.spoofy.esportclash.team.domain.viewmodel.TeamViewModel;
import com.spoofy.esportclash.team.infrastucture.spring.dto.AddPlayerToTeamDTO;
import com.spoofy.esportclash.team.infrastucture.spring.dto.CreateTeamDTO;
import com.spoofy.esportclash.team.infrastucture.spring.dto.RemovePlayerFromTeamDTO;
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

    @GetMapping("/{id}")
    public ResponseEntity<TeamViewModel> getTeamById(@PathVariable String id) {
        var result = pipeline.send(new GetTeamByIdCommand(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IdResponse> createTeam(@RequestBody CreateTeamDTO dto) {
        var result = pipeline.send(new CreateTeamCommand(dto.getName()));

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable String id) {
        pipeline.send(new DeleteTeamCommand(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/add-player-to-team")
    public ResponseEntity<Void> addPlayerToTeam(@RequestBody AddPlayerToTeamDTO dto) {
        pipeline.send(new AddPlayerToTeamCommand(
                dto.getTeamId(),
                dto.getPlayerId(),
                dto.getRole()
        ));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/remove-player-from-team")
    public ResponseEntity<Void> removePlayerFromTeam(@RequestBody RemovePlayerFromTeamDTO dto) {
        pipeline.send(new RemovePlayerFromTeamCommand(
                dto.getTeamId(),
                dto.getPlayerId()
        ));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
