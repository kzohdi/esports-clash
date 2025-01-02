package com.spoofy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.core.domain.viewmodels.IdResponse;

public record CreateTeamCommand(String name) implements Command<IdResponse> {
}
