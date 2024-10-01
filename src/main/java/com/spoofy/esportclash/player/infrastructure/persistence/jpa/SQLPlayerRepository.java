package com.spoofy.esportclash.player.infrastructure.persistence.jpa;

import com.spoofy.esportclash.core.infrastructure.persistence.jpa.SQLBaseRepository;
import com.spoofy.esportclash.player.application.ports.PlayerRepository;
import com.spoofy.esportclash.player.domain.model.Player;
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
