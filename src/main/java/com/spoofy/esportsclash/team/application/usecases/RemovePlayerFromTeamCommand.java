package com.spoofy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;

public record RemovePlayerFromTeamCommand(String playerId, String teamId) implements Command<Voidy> {
}
