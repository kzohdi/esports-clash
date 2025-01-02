package com.spoofy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.spoofy.esportsclash.team.domain.models.Role;

public record AddPlayerToTeamCommand(String teamId, String playerId, Role role) implements Command<Voidy> {
}
