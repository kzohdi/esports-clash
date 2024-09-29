package com.spoofy.esportclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportclash.core.domain.viewmodel.IdResponse;
import com.spoofy.esportclash.player.application.ports.PlayerRepository;
import com.spoofy.esportclash.player.domain.model.Player;

import java.util.UUID;

public class CreatePlayerCommandHandler implements Command.Handler<CreatePlayerCommand, IdResponse> {

    private final PlayerRepository repository;

    public CreatePlayerCommandHandler(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public IdResponse handle(CreatePlayerCommand command) {
        var player = new Player(UUID.randomUUID().toString(), command.getName());

        repository.save(player);

        return new IdResponse(player.getId());
    }
}
