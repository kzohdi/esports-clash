package com.spoofy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemovePlayerFromTeamCommandHandler implements Command.Handler<RemovePlayerFromTeamCommand, Voidy> {

    private final TeamRepository teamRepository;

    @Override
    public Voidy handle(RemovePlayerFromTeamCommand removePlayerFromTeamCommand) {
        var team = teamRepository.findById(removePlayerFromTeamCommand.teamId())
                .orElseThrow(() -> new NotFoundException("Team", removePlayerFromTeamCommand.teamId()));

        team.removeMember(removePlayerFromTeamCommand.playerId());

        teamRepository.save(team);

        return null;
    }
}
