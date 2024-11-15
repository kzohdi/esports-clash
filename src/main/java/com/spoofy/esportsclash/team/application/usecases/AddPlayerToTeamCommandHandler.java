package com.spoofy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.spoofy.esportsclash.core.domain.exceptions.BadRequestException;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddPlayerToTeamCommandHandler implements Command.Handler<AddPlayerToTeamCommand, Voidy> {

    private final TeamRepository teamRepository;

    private final PlayerRepository playerRepository;

    @Override
    public Voidy handle(AddPlayerToTeamCommand createTeamCommand) {
        var team = teamRepository.findById(createTeamCommand.teamId())
                .orElseThrow(() -> new NotFoundException("Team", createTeamCommand.teamId()));

        if (!playerRepository.existsById(createTeamCommand.playerId())) {
            throw new NotFoundException("Player", createTeamCommand.playerId());
        }

        if (teamRepository.existsByPlayerId(createTeamCommand.playerId())) {
            throw new BadRequestException(
                    String.format(
                            "Player %s is already in a team",
                            createTeamCommand.playerId()
                    )
            );
        }

        team.addMember(createTeamCommand.playerId(), createTeamCommand.role());

        teamRepository.save(team);

        return null;
    }
}
