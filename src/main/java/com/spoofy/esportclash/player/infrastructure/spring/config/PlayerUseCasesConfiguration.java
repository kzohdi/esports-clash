package com.spoofy.esportclash.player.infrastructure.spring.config;

import com.spoofy.esportclash.player.application.ports.PlayerRepository;
import com.spoofy.esportclash.player.application.usecases.CreatePlayerCommandHandler;
import com.spoofy.esportclash.player.application.usecases.DeletePlayerCommandHandler;
import com.spoofy.esportclash.player.application.usecases.GetPlayerByIdCommandHandler;
import com.spoofy.esportclash.player.application.usecases.RenamePlayerCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerUseCasesConfiguration {

    @Bean
    public CreatePlayerCommandHandler createPlayerCommandHandler(PlayerRepository playerRepository) {
        return new CreatePlayerCommandHandler(playerRepository);
    }

    @Bean
    public RenamePlayerCommandHandler renamePlayerCommandHandler(PlayerRepository playerRepository) {
        return new RenamePlayerCommandHandler(playerRepository);
    }

    @Bean
    public DeletePlayerCommandHandler deletePlayerCommandHandler(PlayerRepository playerRepository) {
        return new DeletePlayerCommandHandler(playerRepository);
    }

    @Bean
    public GetPlayerByIdCommandHandler getPlayerByIdCommandHandler(PlayerRepository playerRepository) {
        return new GetPlayerByIdCommandHandler(playerRepository);
    }
}
