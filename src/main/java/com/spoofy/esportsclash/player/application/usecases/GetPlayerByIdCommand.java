package com.spoofy.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.player.domain.viewmodels.PlayerViewModel;

public record GetPlayerByIdCommand(String id) implements Command<PlayerViewModel> {
}
