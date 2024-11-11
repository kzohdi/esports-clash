package com.spoofy.esportsclash.player.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.core.infrastructure.persistence.jpa.SQLBaseEntityMapper;
import com.spoofy.esportsclash.player.domain.model.Player;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SQLPlayerMapper extends SQLBaseEntityMapper<Player, SQLPlayer> {
}
