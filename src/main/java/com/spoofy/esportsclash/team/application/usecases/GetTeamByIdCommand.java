package com.spoofy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.team.domain.viewmodel.TeamViewModel;

public record GetTeamByIdCommand(String id) implements Command<TeamViewModel> {
}