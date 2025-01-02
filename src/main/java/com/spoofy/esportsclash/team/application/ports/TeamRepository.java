package com.spoofy.esportsclash.team.application.ports;

import com.spoofy.esportsclash.core.infrastructure.persistence.BaseRepository;
import com.spoofy.esportsclash.team.domain.models.Team;

import java.util.Optional;

public interface TeamRepository extends BaseRepository<Team> {
    Optional<Team> findByPlayerId(String id);
}
