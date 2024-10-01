package com.spoofy.esportclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportclash.core.domain.exceptions.BadRequestException;
import com.spoofy.esportclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportclash.player.application.ports.PlayerRepository;
import com.spoofy.esportclash.team.application.ports.TeamRepository;

public class AddPlayerToTeamCommandHandler implements Command.Handler<AddPlayerToTeamCommand, Void> {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public AddPlayerToTeamCommandHandler(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Void handle(AddPlayerToTeamCommand command) {
        var team = teamRepository
                .findById(command.getTeamId())
                .orElseThrow(() -> new NotFoundException("Team", command.getTeamId()));

        var player = playerRepository
                .findById(command.getPlayerId())
                .orElseThrow(() -> new NotFoundException("Player", command.getPlayerId()));

        var teamPlayerBelongsTo = teamRepository.findByPlayerId(command.getPlayerId());

        if (teamPlayerBelongsTo.isPresent()) {
            throw new BadRequestException(
                    String.format(
                            "Player %s is already in another team",
                            command.getPlayerId()
                    )
            );
        }

        team.addMember(player.getId(), command.getRole());

        teamRepository.save(team);

        return null;
    }
}
