package com.spoofy.esportclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportclash.team.application.ports.TeamQueries;
import com.spoofy.esportclash.team.domain.viewmodel.TeamViewModel;

public class GetTeamByIdCommandHandler implements Command.Handler<GetTeamByIdCommand, TeamViewModel> {

    private final TeamQueries teamQueries;

    public GetTeamByIdCommandHandler(TeamQueries teamQueries) {
        this.teamQueries = teamQueries;
    }

    @Override
    public TeamViewModel handle(GetTeamByIdCommand getTeamByIdCommand) {
        return teamQueries.getTeamById(getTeamByIdCommand.getId());
    }
}
