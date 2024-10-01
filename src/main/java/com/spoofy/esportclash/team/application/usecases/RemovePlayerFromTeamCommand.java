package com.spoofy.esportclash.team.application.usecases;

import an.awesome.pipelinr.Command;

public class RemovePlayerFromTeamCommand implements Command<Void> {
    private final String teamId;
    private final String playerId;

    public RemovePlayerFromTeamCommand(String teamId, String playerId) {
        this.teamId = teamId;
        this.playerId = playerId;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getPlayerId() {
        return playerId;
    }
}
