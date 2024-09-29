package com.spoofy.esportclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportclash.player.application.ports.PlayerRepository;

public class DeletePlayerCommandHandler implements Command.Handler<DeletePlayerCommand, Void> {

    private final PlayerRepository repository;

    public DeletePlayerCommandHandler(PlayerRepository repository) {
        this.repository = repository;
    }


    @Override
    public Void handle(DeletePlayerCommand command) {
        var player = repository
                .findById(command.getId())
                .orElseThrow(() ->
                        new NotFoundException("Player", command.getId()));
        
        repository.delete(player);

        return null;
    }
}
