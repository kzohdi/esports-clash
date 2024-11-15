package com.spoofy.esportsclash.team.infrastructure.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RemovePlayerFromTeamDTO {
    private String playerId;
    private String teamId;
}
