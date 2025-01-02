package com.spoofy.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;

public class RenamePlayerCommandHandler implements Command.Handler<RenamePlayerCommand, Voidy> {

    private final PlayerRepository repository;

    public RenamePlayerCommandHandler(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Voidy handle(RenamePlayerCommand command) {
        var player = repository.findById(command.id())
                .orElseThrow(() -> new NotFoundException("Player", command.id()));

        player.rename(command.newName());

        repository.save(player);

        return null;
    }
}
