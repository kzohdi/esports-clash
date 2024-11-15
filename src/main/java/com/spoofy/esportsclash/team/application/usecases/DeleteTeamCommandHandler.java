package com.spoofy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteTeamCommandHandler implements Command.Handler<DeleteTeamCommand, Voidy> {

    private final TeamRepository teamRepository;

    @Override
    public Voidy handle(DeleteTeamCommand deleteTeamCommand) {
        if (!teamRepository.existsById(deleteTeamCommand.id())) {
            throw new NotFoundException("Team", deleteTeamCommand.id());
        }

        teamRepository.deleteById(deleteTeamCommand.id());

        return null;
    }
}
