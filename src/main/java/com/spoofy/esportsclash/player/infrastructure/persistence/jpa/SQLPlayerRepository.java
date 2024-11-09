package com.spoofy.esportsclash.player.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.player.application.port.PlayerRepository;
import com.spoofy.esportsclash.player.domain.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SQLPlayerRepository implements PlayerRepository {

    private final SQLPlayerDataAccessor dataAccessor;
    private final PlayerMapper mapper;

    @Override
    public void save(Player player) {
        var sqlPlayer = mapper.toSQLPlayer(player);

        dataAccessor.save(sqlPlayer);
    }

    @Override
    public Player findById(String id) {
        return dataAccessor
                .findById(id)
                .map(mapper::toPlayer)
                .orElse(null);
    }
}
