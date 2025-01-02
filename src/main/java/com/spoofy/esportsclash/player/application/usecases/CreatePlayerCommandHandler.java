package com.spoofy.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.core.domain.viewmodels.IdResponse;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.models.Player;

import java.util.UUID;

public class CreatePlayerCommandHandler implements Command.Handler<CreatePlayerCommand, IdResponse> {

    private final PlayerRepository repository;

    public CreatePlayerCommandHandler(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public IdResponse handle(CreatePlayerCommand command) {
        var player = new Player(
                UUID.randomUUID().toString(),
                command.name()
        );

        repository.save(player);

        return new IdResponse(player.getId());
    }
}
