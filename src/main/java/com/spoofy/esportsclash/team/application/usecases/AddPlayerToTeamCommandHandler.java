package com.spoofy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.spoofy.esportsclash.core.domain.exceptions.BadRequestException;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;

public class AddPlayerToTeamCommandHandler implements Command.Handler<AddPlayerToTeamCommand, Voidy> {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public AddPlayerToTeamCommandHandler(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Voidy handle(AddPlayerToTeamCommand command) {
        var team = teamRepository.findById(command.teamId())
                .orElseThrow(() -> new NotFoundException("Team", command.teamId()));

        var player = playerRepository.findById(command.playerId())
                        .orElseThrow(() -> new NotFoundException("Player", command.playerId()));

        var teamPlayerBelongsTo = teamRepository.findByPlayerId(command.playerId());
        if (teamPlayerBelongsTo.isPresent()) {
            throw new BadRequestException("This player is already in a team");
        }

        team.addMember(player.getId(), command.role());

        teamRepository.save(team);

        return null;
    }
}
