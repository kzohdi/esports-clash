package com.spoofy.esportclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportclash.core.domain.viewmodel.IdResponse;
import com.spoofy.esportclash.team.application.ports.TeamRepository;
import com.spoofy.esportclash.team.domain.model.Team;

import java.util.UUID;

public class CreateTeamCommandHandler implements Command.Handler<CreateTeamCommand, IdResponse> {

    private final TeamRepository teamRepository;

    public CreateTeamCommandHandler(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public IdResponse handle(CreateTeamCommand command) {
        var team = new Team(
                UUID.randomUUID().toString(),
                command.getName()
        );

        teamRepository.save(team);

        return new IdResponse(team.getId());
    }
}
