package com.spoofy.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.core.domain.viewmodel.IdResponse;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreatePlayerCommandHandler implements Command.Handler<CreatePlayerCommand, IdResponse> {

    private final PlayerRepository playerRepository;

    public IdResponse handle(CreatePlayerCommand command) {
        var player = new Player(
                UUID.randomUUID().toString(),
                command.name()
        );

        playerRepository.save(player);

        return new IdResponse(player.getId());
    }
}
