package com.spoofy.esportsclash.team.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.core.infrastructure.persistence.jpa.SQLBaseRepository;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.domain.models.Team;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class SQLTeamRepository extends SQLBaseRepository<Team> implements TeamRepository {

    public SQLTeamRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Team> getEntityClass() {
        return Team.class;
    }

    @Override
    public Optional<Team> findByPlayerId(String id) {
        var query = entityManager.createQuery(
                "SELECT t FROM Team t " +
                        "JOIN t.members m " +
                        "WHERE m.playerId = :playerId",
                Team.class);

        query.setParameter("playerId", id);

        return query.getResultList()
                .stream()
                .findFirst();
    }
}
