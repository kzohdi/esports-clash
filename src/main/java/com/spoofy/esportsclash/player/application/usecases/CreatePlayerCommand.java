package com.spoofy.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.core.domain.viewmodels.IdResponse;

public record CreatePlayerCommand(String name) implements Command<IdResponse> {
}
