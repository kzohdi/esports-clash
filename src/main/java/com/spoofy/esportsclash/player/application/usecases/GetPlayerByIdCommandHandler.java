package com.spoofy.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.player.application.port.PlayerRepository;
import com.spoofy.esportsclash.player.domain.mapper.PlayerMapper;
import com.spoofy.esportsclash.player.domain.viewmodel.PlayerViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPlayerByIdCommandHandler implements Command.Handler<GetPlayerByIdCommand, PlayerViewModel> {

    private final PlayerRepository playerRepository;

    private final PlayerMapper mapper;

    public PlayerViewModel handle(GetPlayerByIdCommand command) {
        return playerRepository.findById(command.id()).map(mapper::toPlayerViewModel)
                .orElseThrow(() -> new NotFoundException("Player", command.id()));
    }
}
