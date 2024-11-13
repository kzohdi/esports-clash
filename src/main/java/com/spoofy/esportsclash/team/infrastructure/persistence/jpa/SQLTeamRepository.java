package com.spoofy.esportsclash.team.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.core.infrastructure.persistence.jpa.SQLBaseRepository;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.domain.model.Team;
import org.springframework.stereotype.Repository;

@Repository
public class SQLTeamRepository extends SQLBaseRepository<Team, SQLTeam, SQLTeamDataAccessor> implements TeamRepository {

    public SQLTeamRepository(SQLTeamMapper mapper, SQLTeamDataAccessor dataAccessor) {
        super(mapper, dataAccessor);
    }

    @Override
    public boolean existsByPlayerId(String playerId) {
        return dataAccessor.existsByMembersPlayerId(playerId);
    }
}
