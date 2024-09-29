package com.spoofy.esportclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportclash.player.application.ports.PlayerRepository;
import com.spoofy.esportclash.player.domain.viewmodel.PlayerViewModel;

public class GetPlayerByIdCommandHandler implements Command.Handler<GetPlayerByIdCommand, PlayerViewModel> {

    private final PlayerRepository repository;

    public GetPlayerByIdCommandHandler(PlayerRepository repository) {
        this.repository = repository;
    }


    @Override
    public PlayerViewModel handle(GetPlayerByIdCommand command) {
        var player = repository
                .findById(command.getId())
                .orElseThrow(() ->
                        new NotFoundException("Player", command.getId()));

        return new PlayerViewModel(player.getId(), player.getName());
    }
}
