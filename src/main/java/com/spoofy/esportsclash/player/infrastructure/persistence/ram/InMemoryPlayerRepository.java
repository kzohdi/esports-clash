package com.spoofy.esportsclash.player.infrastructure.persistence.ram;

import com.spoofy.esportsclash.core.infrastructure.persistence.ram.InMemoryBaseRepository;
import com.spoofy.esportsclash.player.application.port.PlayerRepository;
import com.spoofy.esportsclash.player.domain.model.Player;

public class InMemoryPlayerRepository extends InMemoryBaseRepository<Player> implements PlayerRepository {
}
