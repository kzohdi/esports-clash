package com.spoofy.esportsclash.team.infrastructure.persistence.ram;

import com.spoofy.esportsclash.core.infrastructure.persistence.ram.InMemoryBaseRepository;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.domain.model.Team;

public class InMemoryTeamRepository extends InMemoryBaseRepository<Team> implements TeamRepository {
    @Override
    public boolean existsByPlayerId(String playerId) {
        return models.values()
                .stream()
                .anyMatch(team -> team.hasMember(playerId));
    }
}
