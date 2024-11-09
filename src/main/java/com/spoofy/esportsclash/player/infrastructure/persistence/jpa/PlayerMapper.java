package com.spoofy.esportsclash.player.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.player.domain.model.Player;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    SQLPlayer toSQLPlayer(Player player);

    Player toPlayer(SQLPlayer sqlPlayer);
}
