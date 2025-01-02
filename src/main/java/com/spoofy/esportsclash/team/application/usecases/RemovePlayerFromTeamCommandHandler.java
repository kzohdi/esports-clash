package com.spoofy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;

public class RemovePlayerFromTeamCommandHandler implements Command.Handler<RemovePlayerFromTeamCommand, Voidy> {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public RemovePlayerFromTeamCommandHandler(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Voidy handle(RemovePlayerFromTeamCommand command) {
        var team = teamRepository.findById(command.teamId())
                .orElseThrow(() -> new NotFoundException("Team", command.teamId()));

        var player = playerRepository.findById(command.playerId())
                        .orElseThrow(() -> new NotFoundException("Player", command.playerId()));

        team.removeMember(player.getId());

        teamRepository.save(team);

        return null;
    }
}
