package com.spoofy.esportclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportclash.team.domain.model.Role;

public class AddPlayerToTeamCommand implements Command<Void> {
    private final String teamId;
    private final String playerId;
    private final Role role;

    public AddPlayerToTeamCommand(String teamId, String playerId, Role role) {
        this.teamId = teamId;
        this.playerId = playerId;
        this.role = role;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Role getRole() {
        return role;
    }
}
