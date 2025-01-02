package com.spoofy.esportsclash.schedule.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.core.domain.viewmodels.IdResponse;
import com.spoofy.esportsclash.schedule.application.ports.ScheduleDayRepository;
import com.spoofy.esportsclash.schedule.domain.models.ScheduleDay;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;

import java.util.UUID;

public class OrganizeMatchCommandHandler implements Command.Handler<OrganizeMatchCommand, IdResponse> {

    private final ScheduleDayRepository scheduleDayRepository;

    private final TeamRepository teamRepository;

    public OrganizeMatchCommandHandler(ScheduleDayRepository scheduleDayRepository, TeamRepository teamRepository) {
        this.scheduleDayRepository = scheduleDayRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public IdResponse handle(OrganizeMatchCommand command) {
        var team1 = teamRepository.findById(command.firstTeamId())
                .orElseThrow(() -> new NotFoundException("Team", command.firstTeamId()));

        var team2 = teamRepository.findById(command.secondTeamId())
                .orElseThrow(() -> new NotFoundException("Team", command.secondTeamId()));

        var scheduleDay = scheduleDayRepository.findByDate(command.date())
                .orElse(new ScheduleDay(UUID.randomUUID().toString(), command.date()));


        var match = scheduleDay.organize(team1, team2, command.moment());

        scheduleDayRepository.save(scheduleDay);

        return new IdResponse(match.getId());
    }
}
