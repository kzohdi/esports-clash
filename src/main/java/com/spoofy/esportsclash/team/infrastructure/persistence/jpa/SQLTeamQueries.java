package com.spoofy.esportsclash.team.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.team.application.ports.TeamQueries;
import com.spoofy.esportsclash.team.domain.models.Team;
import com.spoofy.esportsclash.team.domain.viewmodels.TeamViewModel;
import jakarta.persistence.EntityManager;

public class SQLTeamQueries implements TeamQueries {

    private final EntityManager entityManager;

    public SQLTeamQueries(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public TeamViewModel getTeamById(String id) {
        var query = entityManager.createQuery(
                "SELECT DISTINCT t FROM Team t " +
                        "JOIN FETCH t.members m " +
                        "JOIN FETCH m.player p " +
                        "WHERE t.id = :id", Team.class
        );

        query.setParameter("id", id);

        var result = query.getSingleResult();

        var players = result.getMembers()
                .stream()
                .map(member ->
                        new TeamViewModel.TeamMember(
                                member.getId(),
                                member.getPlayer().getId(),
                                member.getPlayer().getName(),
                                member.getRole().name()
                        )
                )
                .toList();



        return new TeamViewModel(
                id,
                result.getName(),
                players
        );
    }
}
