package com.spoofy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.team.application.ports.TeamQueries;
import com.spoofy.esportsclash.team.domain.viewmodels.TeamViewModel;

public class GetTeamByIdCommandHandler implements Command.Handler<GetTeamByIdCommand, TeamViewModel> {

    private final TeamQueries teamQueries;

    public GetTeamByIdCommandHandler(TeamQueries teamQueries) {
        this.teamQueries = teamQueries;
    }

    @Override
    public TeamViewModel handle(GetTeamByIdCommand command) {
        return teamQueries.getTeamById(command.id());
    }
}
