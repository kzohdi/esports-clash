package com.spoofy.esportsclash.team.domain.viewmodels;

import java.util.List;

public record TeamViewModel(String id, String name, List<TeamMember> members) {
    public record TeamMember(String id, String playerId, String playerName, String role) {}
}
