package com.spoofy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;

public class DeleteTeamCommandHandler implements Command.Handler<DeleteTeamCommand, Voidy> {

    private final TeamRepository repository;

    public DeleteTeamCommandHandler(TeamRepository repository) {
        this.repository = repository;
    }


    @Override
    public Voidy handle(DeleteTeamCommand command) {
        var team = repository.findById(command.id())
                .orElseThrow(() -> new NotFoundException("Team", command.id()));

        repository.delete(team);

        return null;
    }
}
