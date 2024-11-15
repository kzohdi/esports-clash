package com.spoofy.esportsclash.team.application.ports;

import com.spoofy.esportsclash.core.application.port.BaseRepository;
import com.spoofy.esportsclash.team.domain.model.Team;

public interface TeamRepository extends BaseRepository<Team> {
    boolean existsByPlayerId(String playerId);
}
