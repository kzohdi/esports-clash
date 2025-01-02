package com.spoofy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.core.domain.viewmodels.IdResponse;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.domain.models.Team;

import java.util.UUID;

public class CreateTeamCommandHandler implements Command.Handler<CreateTeamCommand, IdResponse> {

    private final TeamRepository repository;

    public CreateTeamCommandHandler(TeamRepository repository) {
        this.repository = repository;
    }

    @Override
    public IdResponse handle(CreateTeamCommand command) {
        var team = new Team(
                UUID.randomUUID().toString(),
                command.name()
        );

        repository.save(team);

        return new IdResponse(team.getId());
    }
}
