package com.spoofy.esportclash.team.infrastucture.spring.dto;

public class RemovePlayerFromTeamDTO {
    private String teamId;
    private String playerId;

    public RemovePlayerFromTeamDTO() {
    }

    public RemovePlayerFromTeamDTO(String teamId, String playerId) {
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
