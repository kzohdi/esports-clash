package com.spoofy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.core.domain.viewmodel.IdResponse;

public record CreateTeamCommand(String name) implements Command<IdResponse> {
}
