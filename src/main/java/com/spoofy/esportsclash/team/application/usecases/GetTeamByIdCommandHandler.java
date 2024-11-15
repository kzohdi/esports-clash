package com.spoofy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.team.application.ports.TeamQueries;
import com.spoofy.esportsclash.team.domain.viewmodel.TeamViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTeamByIdCommandHandler implements Command.Handler<GetTeamByIdCommand, TeamViewModel> {

    private final TeamQueries teamQueries;

    @Override
    public TeamViewModel handle(GetTeamByIdCommand getTeamByIdCommand) {
        return teamQueries.getTeamById(getTeamByIdCommand.id())
                .orElseThrow(() -> new NotFoundException("Team", getTeamByIdCommand.id()));
    }
}
