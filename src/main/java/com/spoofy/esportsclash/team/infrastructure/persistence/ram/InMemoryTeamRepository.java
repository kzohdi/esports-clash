package com.spoofy.esportsclash.team.infrastructure.persistence.ram;

import com.spoofy.esportsclash.core.infrastructure.persistence.ram.InMemoryBaseRepository;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.domain.models.Team;

import java.util.Optional;

public class InMemoryTeamRepository extends InMemoryBaseRepository<Team> implements TeamRepository {

    @Override
    public Optional<Team> findByPlayerId(String id) {
        return models.values().stream()
                .filter(team -> team.hasMember(id))
                .findFirst();
    }

}
