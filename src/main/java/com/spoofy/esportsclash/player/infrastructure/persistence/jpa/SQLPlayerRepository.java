package com.spoofy.esportsclash.player.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.core.infrastructure.persistence.jpa.SQLBaseRepository;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.model.Player;
import org.springframework.stereotype.Repository;

@Repository
public class SQLPlayerRepository extends SQLBaseRepository<Player, SQLPlayer, SQLPlayerDataAccessor> implements PlayerRepository {

    public SQLPlayerRepository(SQLPlayerMapper mapper, SQLPlayerDataAccessor dataAccessor) {
        super(mapper, dataAccessor);
    }
}
