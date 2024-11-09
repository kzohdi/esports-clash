package com.spoofy.esportsclash.player.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.player.application.port.PlayerRepository;
import com.spoofy.esportsclash.player.domain.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SQLPlayerRepository implements PlayerRepository {

    private final SQLPlayerDataAccessor dataAccessor;
    private final SQLPlayerMapper mapper;

    @Override
    public void save(Player player) {
        var sqlPlayer = mapper.toSQLPlayer(player);

        dataAccessor.save(sqlPlayer);
    }

    @Override
    public Optional<Player> findById(String id) {
        return dataAccessor
                .findById(id)
                .map(mapper::toPlayer);
    }

    @Override
    public boolean existsById(String id) {
        return dataAccessor.existsById(id);
    }

    @Override
    public void deleteById(String id) {
        dataAccessor.deleteById(id);
    }

    @Override
    public void clear() {
        dataAccessor.deleteAll();
    }
}
