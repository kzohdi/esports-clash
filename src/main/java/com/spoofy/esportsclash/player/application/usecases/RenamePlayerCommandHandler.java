package com.spoofy.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.player.application.port.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RenamePlayerCommandHandler implements Command.Handler<RenamePlayerCommand, Voidy> {

    private final PlayerRepository playerRepository;

    public Voidy handle(RenamePlayerCommand command) {
        var player = playerRepository.findById(command.id())
                .orElseThrow(() -> new NotFoundException("Player", command.id()));

        player.rename(command.name());

        playerRepository.save(player);

        return null;
    }
}
