package com.spoofy.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;

public class DeletePlayerCommandHandler implements Command.Handler<DeletePlayerCommand, Voidy> {

    private final PlayerRepository repository;

    public DeletePlayerCommandHandler(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Voidy handle(DeletePlayerCommand command) {
        var player = repository.findById(command.id())
                .orElseThrow(() -> new NotFoundException("Player", command.id()));

        repository.delete(player);

        return null;
    }
}
