package com.spoofy.esportsclash.player.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.core.infrastructure.persistence.jpa.SQLBaseRepository;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.models.Player;
import jakarta.persistence.EntityManager;

public class SQLPlayerRepository extends SQLBaseRepository<Player> implements PlayerRepository {

    public SQLPlayerRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Player> getEntityClass() {
        return Player.class;
    }
}
