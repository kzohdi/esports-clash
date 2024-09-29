package com.spoofy.esportclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportclash.player.application.ports.PlayerRepository;

public class RenamePlayerCommandHandler implements Command.Handler<RenamePlayerCommand, Void> {

    private final PlayerRepository repository;

    public RenamePlayerCommandHandler(PlayerRepository repository) {
        this.repository = repository;
    }


    @Override
    public Void handle(RenamePlayerCommand command) {
        var player = repository
                .findById(command.getId())
                .orElseThrow(() ->
                        new NotFoundException("Player", command.getId()));

        player.rename(command.getName());

        repository.save(player);

        return null;
    }
}
