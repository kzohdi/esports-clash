package com.spoofy.esportsclash.player.infrastructure.persistence.ram;

import com.spoofy.esportsclash.player.application.port.PlayerRepository;
import com.spoofy.esportsclash.player.domain.model.Player;

import java.util.HashMap;
import java.util.Map;

public class InMemoryPlayerRepository implements PlayerRepository {

    private final Map<String, Player> players = new HashMap<>();

    @Override
    public void save(Player player) {
        players.put(player.getId(), player);
    }

    @Override
    public Player findById(String id) {
        return players.get(id);
    }
}
