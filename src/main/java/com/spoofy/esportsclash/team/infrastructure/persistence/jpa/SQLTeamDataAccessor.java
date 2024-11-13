package com.spoofy.esportsclash.team.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.core.infrastructure.persistence.jpa.SQLBaseEntityDataAccessor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SQLTeamDataAccessor extends SQLBaseEntityDataAccessor<SQLTeam> {

    boolean existsByMembersPlayerId(String playerId);

    @Query("SELECT t FROM SQLTeam t JOIN FETCH t.members m JOIN FETCH m.player WHERE t.id = :id")
    Optional<SQLTeam> findByIdAndFetchMembersAndPlayers(String id);
}
