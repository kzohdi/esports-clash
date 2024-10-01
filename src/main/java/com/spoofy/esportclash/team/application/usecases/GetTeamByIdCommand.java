package com.spoofy.esportclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportclash.team.domain.viewmodel.TeamViewModel;

public class GetTeamByIdCommand implements Command<TeamViewModel> {
    private final String id;

    public GetTeamByIdCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
