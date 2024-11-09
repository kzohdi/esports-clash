package com.spoofy.esportsclash.player.infrastructure.persistence.ram;

import com.spoofy.esportsclash.player.application.port.PlayerRepository;
import com.spoofy.esportsclash.player.domain.model.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryPlayerRepository implements PlayerRepository {

    private final Map<String, Player> players = new HashMap<>();

    @Override
    public void save(Player player) {
        players.put(player.getId(), player);
    }

    @Override
    public Optional<Player> findById(String id) {
        return Optional.ofNullable(players.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return players.containsKey(id);
    }

    @Override
    public void deleteById(String id) {
        players.remove(id);
    }

    @Override
    public void clear() {
        players.clear();
    }
}
