package com.spoofy.esportclash.player.infrastructure.persistence.ram;

import com.spoofy.esportclash.core.infrastructure.persistence.ram.InMemoryBaseRepository;
import com.spoofy.esportclash.player.application.ports.PlayerRepository;
import com.spoofy.esportclash.player.domain.model.Player;

public class InMemoryPlayerRepository extends InMemoryBaseRepository<Player> implements PlayerRepository {
}
