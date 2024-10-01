package com.spoofy.esportclash.team.infrastucture.spring.dto;

import com.spoofy.esportclash.team.domain.model.Role;

public class AddPlayerToTeamDTO {
    private String teamId;
    private String playerId;
    private String role;

    public AddPlayerToTeamDTO() {
    }

    public AddPlayerToTeamDTO(String teamId, String playerId, String role) {
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
        return Role.fromString(role);
    }
}
