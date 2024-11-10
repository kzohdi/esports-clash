package com.spoofy.esportsclash.player.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.core.infrastructure.persistence.jpa.SQLBaseRepository;
import com.spoofy.esportsclash.player.application.port.PlayerRepository;
import com.spoofy.esportsclash.player.domain.model.Player;
import org.springframework.stereotype.Repository;

@Repository
public class SQLPlayerRepository extends SQLBaseRepository<Player, SQLPlayer> implements PlayerRepository {

    public SQLPlayerRepository(SQLPlayerMapper mapper, SQLPlayerDataAccessor dataAccessor) {
        super(mapper, dataAccessor);
    }
}
