package com.spoofy.esportclash.team.infrastucture.persistence.jpa;

import com.spoofy.esportclash.core.infrastructure.persistence.jpa.SQLBaseRepository;
import com.spoofy.esportclash.team.application.ports.TeamRepository;
import com.spoofy.esportclash.team.domain.model.Team;
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
    public Optional<Team> findByPlayerId(String playerId) {
        var query = entityManager.createQuery(
                "SELECT t " +
                        "FROM Team t " +
                        "JOIN t.members m " +
                        "WHERE m.playerId = :playerId", Team.class);

        query.setParameter("playerId", playerId);

        return query.getResultList()
                .stream()
                .findFirst();
    }
}
