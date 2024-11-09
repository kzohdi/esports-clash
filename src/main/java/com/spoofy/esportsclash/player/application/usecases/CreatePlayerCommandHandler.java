package com.spoofy.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.player.application.port.PlayerRepository;
import com.spoofy.esportsclash.player.domain.model.Player;
import com.spoofy.esportsclash.player.domain.viewmodel.IdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreatePlayerCommandHandler implements Command.Handler<CreatePlayerCommand, IdResponse> {

    private final PlayerRepository playerRepository;

    public IdResponse handle(CreatePlayerCommand command) {
        var player = new Player(
                UUID.randomUUID().toString(),
                command.getName()
        );

        playerRepository.save(player);

        return new IdResponse(player.getId());
    }
}
