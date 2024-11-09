package com.spoofy.esportsclash.player.application.port;

import com.spoofy.esportsclash.player.domain.model.Player;

public interface PlayerRepository {
    void save(Player player);

    Player findById(String id);
}
