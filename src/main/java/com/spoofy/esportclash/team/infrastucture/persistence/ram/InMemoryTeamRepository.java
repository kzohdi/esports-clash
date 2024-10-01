package com.spoofy.esportclash.team.infrastucture.persistence.ram;

import com.spoofy.esportclash.core.infrastructure.persistence.ram.InMemoryBaseRepository;
import com.spoofy.esportclash.team.application.ports.TeamRepository;
import com.spoofy.esportclash.team.domain.model.Team;

import java.util.Optional;

public class InMemoryTeamRepository extends InMemoryBaseRepository<Team> implements TeamRepository {

    @Override
    public Optional<Team> findByPlayerId(String playerId) {
        return entities.values()
                .stream()
                .filter(team ->
                        team.getMembers()
                                .stream()
                                .anyMatch(member -> member.getPlayerId().equals(playerId)))
                .findFirst();
    }

}
