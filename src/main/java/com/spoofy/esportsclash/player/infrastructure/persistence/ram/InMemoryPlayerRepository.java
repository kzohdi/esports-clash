package com.spoofy.esportsclash.player.infrastructure.persistence.ram;

import com.spoofy.esportsclash.core.infrastructure.persistence.ram.InMemoryBaseRepository;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.models.Player;

public class InMemoryPlayerRepository extends InMemoryBaseRepository<Player> implements PlayerRepository {
}
