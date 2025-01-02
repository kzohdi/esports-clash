package com.spoofy.esportsclash.schedule.infrastructure.spring.controllers;

import an.awesome.pipelinr.Pipeline;
import com.spoofy.esportsclash.core.domain.viewmodels.IdResponse;
import com.spoofy.esportsclash.schedule.application.usecases.CancelMatchCommand;
import com.spoofy.esportsclash.schedule.application.usecases.OrganizeMatchCommand;
import com.spoofy.esportsclash.schedule.domain.models.Moment;
import com.spoofy.esportsclash.schedule.infrastructure.spring.dtos.CancelMatchDTO;
import com.spoofy.esportsclash.schedule.infrastructure.spring.dtos.OrganizeMatchDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
@Transactional
public class ScheduleController {

    private final Pipeline pipeline;

    public ScheduleController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @PostMapping("/organize-match")
    public ResponseEntity<IdResponse> organizeMatch(@RequestBody OrganizeMatchDTO dto) {
        var result = pipeline.send(new OrganizeMatchCommand(
                dto.date(),
                Moment.valueOf(dto.moment()),
                dto.firstTeamId(),
                dto.secondTeamId()
        ));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/cancel-match")
    public ResponseEntity<IdResponse> cancelMatch(@RequestBody CancelMatchDTO dto) {
        pipeline.send(new CancelMatchCommand(dto.matchId()));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
