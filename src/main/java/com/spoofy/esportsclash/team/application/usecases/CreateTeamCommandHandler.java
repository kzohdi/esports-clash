package com.spoofy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.core.domain.viewmodel.IdResponse;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.domain.model.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateTeamCommandHandler implements Command.Handler<CreateTeamCommand, IdResponse> {

    private final TeamRepository teamRepository;

    @Override
    public IdResponse handle(CreateTeamCommand createTeamCommand) {
        var team = new Team(
                UUID.randomUUID().toString(),
                createTeamCommand.name()
        );

        teamRepository.save(team);

        return new IdResponse(team.getId());
    }
}
