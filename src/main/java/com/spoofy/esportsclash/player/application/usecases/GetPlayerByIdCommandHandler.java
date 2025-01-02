package com.spoofy.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.viewmodels.PlayerViewModel;

public class GetPlayerByIdCommandHandler implements Command.Handler<GetPlayerByIdCommand, PlayerViewModel> {

    private final PlayerRepository repository;

    public GetPlayerByIdCommandHandler(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public PlayerViewModel handle(GetPlayerByIdCommand command) {
        return repository.findById(command.id())
                .map(player -> new PlayerViewModel(player.getId(), player.getName()))
                .orElseThrow(() -> new NotFoundException("Player", command.id()));
    }
}
