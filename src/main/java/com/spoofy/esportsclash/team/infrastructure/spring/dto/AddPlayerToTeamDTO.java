package com.spoofy.esportsclash.team.infrastructure.spring.dto;

import com.spoofy.esportsclash.team.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddPlayerToTeamDTO {
    private String playerId;
    private String teamId;
    private Role role;
}
