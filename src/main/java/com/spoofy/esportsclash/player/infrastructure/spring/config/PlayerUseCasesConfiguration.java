package com.spoofy.esportsclash.player.infrastructure.spring.config;

import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.application.usecases.CreatePlayerCommandHandler;
import com.spoofy.esportsclash.player.application.usecases.DeletePlayerCommandHandler;
import com.spoofy.esportsclash.player.application.usecases.GetPlayerByIdCommandHandler;
import com.spoofy.esportsclash.player.application.usecases.RenamePlayerCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerUseCasesConfiguration {

    @Bean
    CreatePlayerCommandHandler createPlayerCommandHandler(PlayerRepository playerRepository) {
        return new CreatePlayerCommandHandler(playerRepository);
    }

    @Bean
    DeletePlayerCommandHandler deletePlayerCommandHandler(PlayerRepository playerRepository) {
        return new DeletePlayerCommandHandler(playerRepository);
    }

    @Bean
    RenamePlayerCommandHandler renamePlayerCommandHandler(PlayerRepository playerRepository) {
        return new RenamePlayerCommandHandler(playerRepository);
    }

    @Bean
    GetPlayerByIdCommandHandler getPlayerByIdCommandHandler(PlayerRepository playerRepository) {
        return new GetPlayerByIdCommandHandler(playerRepository);
    }
}
