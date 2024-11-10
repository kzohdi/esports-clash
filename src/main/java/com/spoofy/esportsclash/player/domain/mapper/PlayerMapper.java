package com.spoofy.esportsclash.player.domain.mapper;

import com.spoofy.esportsclash.player.domain.model.Player;
import com.spoofy.esportsclash.player.domain.viewmodel.PlayerViewModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    PlayerViewModel toPlayerViewModel(Player player);
}
