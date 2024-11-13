package com.spoofy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.spoofy.esportsclash.team.domain.model.Role;

public record AddPlayerToTeamCommand(String playerId, String teamId, Role role) implements Command<Voidy> {
}
