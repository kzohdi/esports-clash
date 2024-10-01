package com.spoofy.esportclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportclash.team.application.ports.TeamRepository;

public class RemovePlayerFromTeamCommandHandler implements Command.Handler<RemovePlayerFromTeamCommand, Void> {

    private final TeamRepository teamRepository;

    public RemovePlayerFromTeamCommandHandler(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Void handle(RemovePlayerFromTeamCommand command) {
        var team = teamRepository
                .findById(command.getTeamId())
                .orElseThrow(() -> new NotFoundException("Team", command.getTeamId()));

        team.removeMember(command.getPlayerId());

        teamRepository.save(team);

        return null;
    }
}
