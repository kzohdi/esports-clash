package com.spoofy.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.player.application.port.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePlayerCommandHandler implements Command.Handler<DeletePlayerCommand, Voidy> {

    private final PlayerRepository playerRepository;

    public Voidy handle(DeletePlayerCommand command) {
        if (!playerRepository.existsById(command.id())) {
            throw new NotFoundException("Player", command.id());
        }

        playerRepository.deleteById(command.id());
        
        return null;
    }
}
