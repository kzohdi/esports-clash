package com.spoofy.esportsclash.schedule.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.schedule.application.ports.ScheduleDayRepository;

public class CancelMatchCommandHandler implements Command.Handler<CancelMatchCommand, Voidy> {

    private final ScheduleDayRepository scheduleDayRepository;

    public CancelMatchCommandHandler(ScheduleDayRepository scheduleDayRepository) {
        this.scheduleDayRepository = scheduleDayRepository;
    }

    @Override
    public Voidy handle(CancelMatchCommand command) {
        var scheduleDay = scheduleDayRepository.findByMatchId(command.matchId())
                .orElseThrow(() -> new NotFoundException("Match", command.matchId()));

        scheduleDay.cancel(command.matchId());

        if (scheduleDay.hasMatches()) {
            scheduleDayRepository.save(scheduleDay);
        } else {
            scheduleDayRepository.delete(scheduleDay);
        }

        return null;
    }
}
