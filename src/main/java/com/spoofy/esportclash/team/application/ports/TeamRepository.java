package com.spoofy.esportclash.team.application.ports;

import com.spoofy.esportclash.core.infrastructure.persistence.BaseRepository;
import com.spoofy.esportclash.team.domain.model.Team;

import java.util.Optional;

public interface TeamRepository extends BaseRepository<Team> {
    Optional<Team> findByPlayerId(String playerId);
}
